package hu.bme.aut.android.brewbuddy.presentation.activeprocess.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.brewbuddy.domain.model.process.BrewStep
import hu.bme.aut.android.brewbuddy.domain.repository.BrewProcessRepository
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
        checkNotNull(
            savedStateHandle["processId"]
        )

    private val _steps =
        MutableStateFlow<List<BrewStep>>(
            emptyList()
        )

    val steps:
            StateFlow<List<BrewStep>>
        = _steps

    private val _currentStepIndex =
        MutableStateFlow(0)

    val currentStepIndex:
            StateFlow<Int>
        = _currentStepIndex

    init {

        observeSteps()
    }

    private fun observeSteps() {

        repository
            .observeStepsForProcess(
                processId
            )
            .onEach {

                _steps.value = it
            }
            .launchIn(viewModelScope)
    }

    fun nextStep() {

        if (
            _currentStepIndex.value <
            _steps.value.lastIndex
        ) {

            _currentStepIndex.value++
        }
    }

    fun previousStep() {

        if (_currentStepIndex.value > 0) {

            _currentStepIndex.value--
        }
    }

    fun completeCurrentStep() {

        val currentStep =
            _steps.value.getOrNull(
                _currentStepIndex.value
            )
                ?: return

        viewModelScope.launch {

            repository.completeStep(
                currentStep.id
            )

            if (
                _currentStepIndex.value <
                _steps.value.lastIndex
            ) {

                _currentStepIndex.value++

            } else {

                repository.finishProcess(
                    processId
                )
            }
        }
    }
}