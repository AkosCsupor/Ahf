package hu.bme.aut.android.brewbuddy.presentation.process.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.brewbuddy.domain.model.process.BrewProcess
import hu.bme.aut.android.brewbuddy.domain.repository.BrewProcessRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProcessViewModel @Inject constructor(

    private val repository:
        BrewProcessRepository

) : ViewModel() {

    private val _activeProcesses =
        MutableStateFlow<List<BrewProcess>>(
            emptyList()
        )

    val activeProcesses:
            StateFlow<List<BrewProcess>>
        = _activeProcesses

    private val _finishedProcesses =
        MutableStateFlow<List<BrewProcess>>(
            emptyList()
        )

    val finishedProcesses:
            StateFlow<List<BrewProcess>>
        = _finishedProcesses

    init {

        observeActiveProcesses()

        observeFinishedProcesses()
    }

    private fun observeActiveProcesses() {

        repository
            .observeActiveProcesses()
            .onEach {

                _activeProcesses.value = it
            }
            .launchIn(viewModelScope)
    }

    private fun observeFinishedProcesses() {

        repository
            .observeFinishedProcesses()
            .onEach {

                _finishedProcesses.value = it
            }
            .launchIn(viewModelScope)
    }
}