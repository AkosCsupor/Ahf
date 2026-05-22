package hu.bme.aut.android.brewbuddy.presentation.brewhistory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.brewbuddy.domain.model.BrewHistory
import hu.bme.aut.android.brewbuddy.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrewHistoryViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _brewHistory =
        MutableStateFlow<List<BrewHistory>>(
            emptyList()
        )

    val brewHistory:
            StateFlow<List<BrewHistory>>
            = _brewHistory.asStateFlow()

    init {

        repository.getBrewHistory()
            .onEach {

                _brewHistory.value = it
            }
            .launchIn(viewModelScope)
    }

    fun addSampleHistory() {

        viewModelScope.launch {

            repository.insertBrewHistory(
                BrewHistory(
                    id = 0,
                    recipeName = "IPA",
                    brewDate = "2026-05-21",
                    abv = 5.4,
                    notes =
                        "Excellent citrus aroma"
                )
            )
        }
    }
}