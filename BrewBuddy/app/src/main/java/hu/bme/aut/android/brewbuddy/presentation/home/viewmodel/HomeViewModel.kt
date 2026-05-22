package hu.bme.aut.android.brewbuddy.presentation.home.viewmodel

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
class HomeViewModel @Inject constructor(
    private val repository: BrewProcessRepository
) : ViewModel() {

    private val _activeProcesses = MutableStateFlow<List<BrewProcess>>(emptyList())
    val activeProcesses: StateFlow<List<BrewProcess>> = _activeProcesses

    init {
        observeActiveProcesses()
    }

    private fun observeActiveProcesses() {
        repository.observeActiveProcesses()
            .onEach {
                _activeProcesses.value = it
            }
            .launchIn(viewModelScope)
    }
}
