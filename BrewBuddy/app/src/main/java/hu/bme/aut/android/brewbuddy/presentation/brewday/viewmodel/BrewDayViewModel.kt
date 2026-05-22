package hu.bme.aut.android.brewbuddy.presentation.brewday.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.brewbuddy.domain.model.BrewStep
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BrewDayViewModel : ViewModel() {

    private val brewSteps = listOf(

        BrewStep(
            title = "Mash",
            description =
                "Maintain mash temperature",
            durationMinutes = 60
        ),

        BrewStep(
            title = "Boil",
            description =
                "Boil the wort",
            durationMinutes = 90
        ),

        BrewStep(
            title = "Hop Addition",
            description =
                "Add aroma hops",
            durationMinutes = 10
        ),

        BrewStep(
            title = "Cooling",
            description =
                "Cool the wort",
            durationMinutes = 20
        )
    )

    private val _currentStepIndex =
        MutableStateFlow(0)

    val currentStepIndex:
            StateFlow<Int>
            = _currentStepIndex.asStateFlow()

    private val _remainingSeconds =
        MutableStateFlow(
            brewSteps.first()
                .durationMinutes * 60
        )

    val remainingSeconds:
            StateFlow<Int>
            = _remainingSeconds.asStateFlow()

    private val _isRunning =
        MutableStateFlow(false)

    val isRunning:
            StateFlow<Boolean>
            = _isRunning.asStateFlow()

    val currentStep: BrewStep
        get() = brewSteps[_currentStepIndex.value]

    fun startTimer() {

        if (_isRunning.value) return

        _isRunning.value = true

        viewModelScope.launch {

            while (
                _remainingSeconds.value > 0
            ) {

                delay(1000)

                _remainingSeconds.value -= 1
            }

            _isRunning.value = false
        }
    }

    fun nextStep() {

        if (
            _currentStepIndex.value <
            brewSteps.lastIndex
        ) {

            _currentStepIndex.value += 1

            _remainingSeconds.value =
                currentStep.durationMinutes * 60

            _isRunning.value = false
        }
    }

    fun resetTimer() {

        _remainingSeconds.value =
            currentStep.durationMinutes * 60

        _isRunning.value = false
    }
}