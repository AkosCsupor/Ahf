package hu.bme.aut.android.brewbuddy.presentation.activeprocess.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.brewbuddy.domain.model.process.BrewProcess
import hu.bme.aut.android.brewbuddy.domain.model.process.BrewStep
import hu.bme.aut.android.brewbuddy.domain.repository.BrewProcessRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActiveProcessViewModel @Inject constructor(

    private val repository:
        BrewProcessRepository,

    savedStateHandle: SavedStateHandle

) : ViewModel() {

    private val processId: Long =
        savedStateHandle.get<Long>("processId")
            ?: savedStateHandle.get<String>("processId")?.toLongOrNull()
            ?: throw IllegalStateException("processId is missing or invalid")

    val processIdValue: Long
        get() = processId

    private val _steps =
        MutableStateFlow<List<BrewStep>>(
            emptyList()
        )

    private val _process =
        MutableStateFlow<BrewProcess?>(null)

    val steps:
            StateFlow<List<BrewStep>>
        = _steps

    private val _currentStepIndex =
        MutableStateFlow(0)

    val currentStepIndex:
            StateFlow<Int>
        = _currentStepIndex

    private val _selectedStepIndex =
        MutableStateFlow(0)

    val selectedStepIndex:
            StateFlow<Int>
        = _selectedStepIndex

    private val _remainingSeconds =
        MutableStateFlow(0L)

    val remainingSeconds:
            StateFlow<Long>
        = _remainingSeconds

    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused

    private var pausedRemainingSeconds: Long = 0

    init {

        observeProcess()

        observeSteps()

        trackRemainingTime()
    }

    fun togglePause() {
        if (_isPaused.value) {
            // Resume: recalculate currentStepStartedAt so that it reflects the remaining time
            val currentStep = _steps.value.getOrNull(_currentStepIndex.value)
            if (currentStep != null) {
                val newStartedAt = System.currentTimeMillis() - 
                    (currentStep.durationMinutes * 60_000L - pausedRemainingSeconds * 1000L)
                
                viewModelScope.launch {
                    repository.updateCurrentStep(
                        processId = processId,
                        stepIndex = _currentStepIndex.value,
                        startedAt = newStartedAt
                    )
                }
            }
            _isPaused.value = false
        } else {
            // Pause
            pausedRemainingSeconds = _remainingSeconds.value
            _isPaused.value = true
        }
    }

    fun resetTimer() {
        viewModelScope.launch {
            repository.updateCurrentStep(
                processId = processId,
                stepIndex = _currentStepIndex.value,
                startedAt = System.currentTimeMillis()
            )
            _isPaused.value = false
        }
    }

    private fun observeProcess() {

        repository
            .observeProcessById(processId)
            .onEach { process ->

                _process.value = process

                val index =
                    process?.currentStepIndex
                        ?.coerceAtLeast(0)
                        ?: 0

                _currentStepIndex.value = index

                if (_selectedStepIndex.value != index) {

                    _selectedStepIndex.value = index
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeSteps() {

        repository
            .observeStepsForProcess(
                processId
            )
            .onEach {

                _steps.value = it

                val maxIndex =
                    (it.lastIndex).coerceAtLeast(0)

                _selectedStepIndex.value =
                    _selectedStepIndex.value
                        .coerceIn(0, maxIndex)

                _currentStepIndex.value =
                    _currentStepIndex.value
                        .coerceIn(0, maxIndex)
            }
            .launchIn(viewModelScope)
    }

    fun nextStep() {

        if (
            _selectedStepIndex.value <
            _steps.value.lastIndex
        ) {

            _selectedStepIndex.value++
        }
    }

    fun previousStep() {

        if (_selectedStepIndex.value > 0) {

            _selectedStepIndex.value--
        }
    }

    fun jumpToCurrentStep() {

        _selectedStepIndex.value =
            _currentStepIndex.value
    }

    fun isViewingCurrentStep(): Boolean {

        return _selectedStepIndex.value ==
                _currentStepIndex.value
    }

    fun completeCurrentStep(onFinished: () -> Unit = {}) {

        val currentStep =
            _steps.value.getOrNull(
                _currentStepIndex.value
            )
                ?: return

        viewModelScope.launch {

            repository.completeStep(
                currentStep.id
            )

            val nextIndex =
                _currentStepIndex.value + 1

            if (
                nextIndex <=
                _steps.value.lastIndex
            ) {

                repository.updateCurrentStep(
                    processId = processId,
                    stepIndex = nextIndex,
                    startedAt =
                        System.currentTimeMillis()
                )
                _isPaused.value = false

            } else {

                repository.finishProcess(
                    processId
                )
                onFinished()
            }
        }
    }

    private fun trackRemainingTime() {

        viewModelScope.launch {

            while (true) {

                if (!_isPaused.value) {
                    val currentIndex =
                        _currentStepIndex.value

                    val currentStep =
                        _steps.value.getOrNull(currentIndex)

                    if (currentStep == null) {

                        _remainingSeconds.value = 0

                    } else {
                        val startedAt =
                            _process.value
                                ?.currentStepStartedAt
                                ?: System.currentTimeMillis()

                        val finishAt =
                            startedAt +
                                    currentStep.durationMinutes *
                                    60_000L

                        val remaining =
                            ((finishAt -
                                    System.currentTimeMillis()) / 1000L)
                                .coerceAtLeast(0L)

                        _remainingSeconds.value = remaining
                    }
                }

                delay(1_000)
            }
        }
    }
}