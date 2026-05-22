package hu.bme.aut.android.brewbuddy.presentation.activeprocess.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import hu.bme.aut.android.brewbuddy.domain.model.process.BrewProcess
import hu.bme.aut.android.brewbuddy.domain.model.process.BrewStep
import hu.bme.aut.android.brewbuddy.domain.repository.BrewProcessRepository
import hu.bme.aut.android.brewbuddy.worker.BrewTimerScheduler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActiveProcessViewModel @Inject constructor(
    private val repository: BrewProcessRepository,
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val processId: Long =
        savedStateHandle.get<Long>("processId")
            ?: savedStateHandle.get<String>("processId")?.toLongOrNull()
            ?: throw IllegalStateException("processId is missing or invalid")

    val processIdValue: Long
        get() = processId

    private val _steps = MutableStateFlow<List<BrewStep>>(emptyList())
    val steps: StateFlow<List<BrewStep>> = _steps

    private val _process = MutableStateFlow<BrewProcess?>(null)

    private val _currentStepIndex = MutableStateFlow(0)
    val currentStepIndex: StateFlow<Int> = _currentStepIndex

    private val _selectedStepIndex = MutableStateFlow(0)
    val selectedStepIndex: StateFlow<Int> = _selectedStepIndex

    private val _remainingSeconds = MutableStateFlow(0L)
    val remainingSeconds: StateFlow<Long> = _remainingSeconds

    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused

    private var pausedRemainingSeconds: Long = 0

    init {
        observeProcess()
        observeSteps()
        trackRemainingTime()
    }

    fun togglePause() {
        val currentStep = _steps.value.getOrNull(_currentStepIndex.value) ?: return
        
        if (_isPaused.value) {
            // Resume
            val newStartedAt = System.currentTimeMillis() - 
                (currentStep.durationMinutes * 60_000L - pausedRemainingSeconds * 1000L)
            
            viewModelScope.launch {
                repository.updateCurrentStep(
                    processId = processId,
                    stepIndex = _currentStepIndex.value,
                    startedAt = newStartedAt
                )
                _isPaused.value = false
                // Schedule background notification for the remaining time
                BrewTimerScheduler.scheduleTimer(
                    context, processId, currentStep.id, currentStep.title, pausedRemainingSeconds
                )
            }
        } else {
            // Pause
            pausedRemainingSeconds = _remainingSeconds.value
            _isPaused.value = true
            // Cancel background notification
            BrewTimerScheduler.cancelTimer(context, processId, currentStep.id)
        }
    }

    fun resetTimer() {
        val currentStep = _steps.value.getOrNull(_currentStepIndex.value) ?: return
        viewModelScope.launch {
            repository.updateCurrentStep(
                processId = processId,
                stepIndex = _currentStepIndex.value,
                startedAt = System.currentTimeMillis()
            )
            _isPaused.value = false
            BrewTimerScheduler.scheduleTimer(
                context, processId, currentStep.id, currentStep.title, currentStep.durationMinutes * 60L
            )
        }
    }

    private fun observeProcess() {
        repository
            .observeProcessById(processId)
            .onEach { process ->
                val oldIndex = _process.value?.currentStepIndex
                _process.value = process
                val index = process?.currentStepIndex?.coerceAtLeast(0) ?: 0
                _currentStepIndex.value = index

                if (_selectedStepIndex.value != index) {
                    _selectedStepIndex.value = index
                }

                // If step changed, schedule the new timer
                if (oldIndex != index && !_isPaused.value) {
                    val currentStep = _steps.value.getOrNull(index)
                    currentStep?.let {
                        val remaining = calculateRemainingSeconds(it, process?.currentStepStartedAt ?: System.currentTimeMillis())
                        BrewTimerScheduler.scheduleTimer(context, processId, it.id, it.title, remaining)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeSteps() {
        repository
            .observeStepsForProcess(processId)
            .onEach {
                _steps.value = it
                val maxIndex = (it.lastIndex).coerceAtLeast(0)
                _selectedStepIndex.value = _selectedStepIndex.value.coerceIn(0, maxIndex)
                _currentStepIndex.value = _currentStepIndex.value.coerceIn(0, maxIndex)
            }
            .launchIn(viewModelScope)
    }

    private fun calculateRemainingSeconds(step: BrewStep, startedAt: Long): Long {
        val finishAt = startedAt + step.durationMinutes * 60_000L
        return ((finishAt - System.currentTimeMillis()) / 1000L).coerceAtLeast(0L)
    }

    fun nextStep() {
        if (_selectedStepIndex.value < _steps.value.lastIndex) {
            _selectedStepIndex.value++
        }
    }

    fun previousStep() {
        if (_selectedStepIndex.value > 0) {
            _selectedStepIndex.value--
        }
    }

    fun jumpToCurrentStep() {
        _selectedStepIndex.value = _currentStepIndex.value
    }

    fun isViewingCurrentStep(): Boolean = _selectedStepIndex.value == _currentStepIndex.value

    fun completeCurrentStep(onFinished: () -> Unit = {}) {
        val currentStep = _steps.value.getOrNull(_currentStepIndex.value) ?: return
        BrewTimerScheduler.cancelTimer(context, processId, currentStep.id)

        viewModelScope.launch {
            repository.completeStep(currentStep.id)
            val nextIndex = _currentStepIndex.value + 1
            if (nextIndex <= _steps.value.lastIndex) {
                repository.updateCurrentStep(
                    processId = processId,
                    stepIndex = nextIndex,
                    startedAt = System.currentTimeMillis()
                )
                _isPaused.value = false
            } else {
                repository.finishProcess(processId)
                onFinished()
            }
        }
    }

    private fun trackRemainingTime() {
        viewModelScope.launch {
            while (true) {
                if (!_isPaused.value) {
                    val currentIndex = _currentStepIndex.value
                    val currentStep = _steps.value.getOrNull(currentIndex)
                    if (currentStep == null) {
                        _remainingSeconds.value = 0
                    } else {
                        _remainingSeconds.value = calculateRemainingSeconds(
                            currentStep, 
                            _process.value?.currentStepStartedAt ?: System.currentTimeMillis()
                        )
                    }
                }
                delay(1_000)
            }
        }
    }
}
