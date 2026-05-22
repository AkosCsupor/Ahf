package hu.bme.aut.android.brewbuddy.presentation.inventory.viewmodel

import androidx.lifecycle.SavedStateHandle
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
class AddEditInventoryViewModel @Inject constructor(

    private val inventoryRepository:
        InventoryRepository,

    savedStateHandle:
        SavedStateHandle

) : ViewModel() {

    private val ingredientId:
            Long? =
        savedStateHandle
            .get<String>(
                "ingredientId"
            )
            ?.toLongOrNull()

    private val _name =
        MutableStateFlow("")

    val name:
            StateFlow<String>
        = _name

    private val _amount =
        MutableStateFlow("")

    val amount:
            StateFlow<String>
        = _amount

    private val _unit =
        MutableStateFlow("")

    val unit:
            StateFlow<String>
        = _unit

    private val _notes =
        MutableStateFlow("")

    val notes:
            StateFlow<String>
        = _notes

    init {

        loadIngredient()
    }

    private fun loadIngredient() {

        if (ingredientId == null)
            return

        viewModelScope.launch {

            val ingredient =

                inventoryRepository
                    .getIngredientById(
                        ingredientId
                    )
                    ?: return@launch

            _name.value =
                ingredient.name

            _amount.value =
                ingredient.amount
                    .toString()

            _unit.value =
                ingredient.unit

            _notes.value =
                ingredient.notes
                    ?: ""
        }
    }

    fun updateName(
        value: String
    ) {

        _name.value = value
    }

    fun updateAmount(
        value: String
    ) {

        _amount.value = value
    }

    fun updateUnit(
        value: String
    ) {

        _unit.value = value
    }

    fun updateNotes(
        value: String
    ) {

        _notes.value = value
    }

    fun saveIngredient(
        onFinished: () -> Unit
    ) {

        viewModelScope.launch {

            inventoryRepository
                .insertIngredient(

                    Ingredient(

                        id =
                            ingredientId ?: 0,

                        name =
                            name.value,

                        amount =
                            amount.value
                                .toDoubleOrNull()
                                ?: 0.0,

                        unit =
                            unit.value,

                        notes =
                            notes.value
                    )
                )

            onFinished()
        }
    }

    fun deleteIngredient(
        onFinished: () -> Unit
    ) {

        viewModelScope.launch {

            if (ingredientId == null)
                return@launch

            inventoryRepository
                .deleteIngredient(

                    Ingredient(

                        id = ingredientId,

                        name =
                            name.value,

                        amount =
                            amount.value
                                .toDoubleOrNull()
                                ?: 0.0,

                        unit =
                            unit.value,

                        notes =
                            notes.value
                    )
                )

            onFinished()
        }
    }
}