package hu.bme.aut.android.brewbuddy.presentation.inventory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.brewbuddy.domain.model.Ingredient
import hu.bme.aut.android.brewbuddy.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(

    private val inventoryRepository:
    InventoryRepository

) : ViewModel() {

    private val _ingredients =
        MutableStateFlow<List<Ingredient>>(
            emptyList()
        )

    val ingredients:
            StateFlow<List<Ingredient>>
            = _ingredients

    init {

        observeIngredients()
    }

    private fun observeIngredients() {

        viewModelScope.launch {

            inventoryRepository
                .observeIngredients()
                .collect {

                    _ingredients.value = it
                }
        }
    }
}