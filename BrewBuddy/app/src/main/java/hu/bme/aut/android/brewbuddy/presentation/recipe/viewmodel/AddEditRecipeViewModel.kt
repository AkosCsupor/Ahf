package hu.bme.aut.android.brewbuddy.presentation.recipe.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.brewbuddy.domain.model.Recipe
import hu.bme.aut.android.brewbuddy.domain.model.RecipeStep
import hu.bme.aut.android.brewbuddy.domain.model.Ingredient
import hu.bme.aut.android.brewbuddy.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditRecipeViewModel @Inject constructor(

    private val repository:
        RecipeRepository,

    savedStateHandle:
        SavedStateHandle

) : ViewModel() {

    val recipeId: Long? =
        try {
            savedStateHandle.get<Long>("recipeId")?.takeIf { it != -1L }
        } catch (e: ClassCastException) {
            savedStateHandle.get<String>("recipeId")?.toLongOrNull()?.takeIf { it != -1L }
        }

    private val _name =
        MutableStateFlow("")

    val name: StateFlow<String>
        = _name

    private val _style =
        MutableStateFlow("")

    val style: StateFlow<String>
        = _style

    private val _description =
        MutableStateFlow("")

    val description:
            StateFlow<String>
        = _description

    private val _batchSize =
        MutableStateFlow("20")

    val batchSize:
            StateFlow<String>
        = _batchSize

    private val _abv =
        MutableStateFlow("")

    val abv:
            StateFlow<String>
        = _abv

    private val _imageUri =
        MutableStateFlow<String?>(null)

    val imageUri:
            StateFlow<String?>
        = _imageUri

    private val _steps =
        MutableStateFlow<List<RecipeStep>>(
            emptyList()
        )

    val steps:
            StateFlow<List<RecipeStep>>
        = _steps

    private val _ingredients =
        MutableStateFlow<List<Ingredient>>(
            emptyList()
        )

    val ingredients:
            StateFlow<List<Ingredient>>
        = _ingredients

    init {

        loadRecipe()
    }

    private fun loadRecipe() {

        if (recipeId == null)
            return

        viewModelScope.launch {

            val recipe =
                repository
                    .getRecipeById(
                        recipeId
                    )
                    ?: return@launch

            _name.value =
                recipe.name

            _style.value =
                recipe.style

            _description.value =
                recipe.description

            _batchSize.value =
                recipe.batchSize
                    .toString()

            _abv.value =
                recipe.abv
                    ?.toString()
                    ?: ""

            _imageUri.value =
                recipe.imageUri

            _ingredients.value = recipe.ingredients

            repository
                .observeRecipeSteps(
                    recipeId
                )
                .collect {

                    _steps.value = it
                }
        }
    }

    fun updateName(value: String) {
        _name.value = value
    }

    fun updateStyle(value: String) {
        _style.value = value
    }

    fun updateDescription(
        value: String
    ) {
        _description.value = value
    }

    fun updateBatchSize(
        value: String
    ) {
        _batchSize.value = value
    }

    fun updateAbv(value: String) {
        _abv.value = value
    }

    fun updateImageUri(value: String?) {
        _imageUri.value = value
    }

    fun addStep() {

        val updated =

            _steps.value.toMutableList()

        updated.add(

            RecipeStep(

                id = 0,

                recipeId =
                    recipeId ?: 0,

                title =
                    "New Step",

                description = "",

                durationMinutes = 60,

                stepOrder =
                    updated.size
            )
        )

        _steps.value = updated
    }

    fun updateStepTitle(
        index: Int,
        value: String
    ) {

        val updated =
            _steps.value.toMutableList()

        updated[index] =
            updated[index].copy(
                title = value
            )

        _steps.value = updated
    }

    fun updateStepDescription(
        index: Int,
        value: String
    ) {

        val updated =
            _steps.value.toMutableList()

        updated[index] =
            updated[index].copy(
                description = value
            )

        _steps.value = updated
    }

    fun updateStepDuration(
        index: Int,
        value: String
    ) {

        val updated =
            _steps.value.toMutableList()

        updated[index] =
            updated[index].copy(

                durationMinutes =

                    value.toIntOrNull()
                        ?: 0
            )

        _steps.value = updated
    }

    fun deleteStepAt(index: Int) {
        val updated = _steps.value.toMutableList()
        updated.removeAt(index)
        _steps.value = updated
    }

    fun addIngredient() {
        val updated = _ingredients.value.toMutableList()
        updated.add(
            Ingredient(
                id = 0,
                name = "New Ingredient",
                amount = 0.0,
                unit = "g",
                notes = ""
            )
        )
        _ingredients.value = updated
    }

    fun updateIngredientName(index: Int, value: String) {
        val updated = _ingredients.value.toMutableList()
        updated[index] = updated[index].copy(name = value)
        _ingredients.value = updated
    }

    fun updateIngredientAmount(index: Int, value: String) {
        val updated = _ingredients.value.toMutableList()
        updated[index] = updated[index].copy(amount = value.toDoubleOrNull() ?: 0.0)
        _ingredients.value = updated
    }

    fun updateIngredientUnit(index: Int, value: String) {
        val updated = _ingredients.value.toMutableList()
        updated[index] = updated[index].copy(unit = value)
        _ingredients.value = updated
    }

    fun deleteIngredientAt(index: Int) {
        val updated = _ingredients.value.toMutableList()
        updated.removeAt(index)
        _ingredients.value = updated
    }

    fun deleteRecipe(onFinished: () -> Unit) {
        viewModelScope.launch {
            if (recipeId != null) {
                val recipe = repository.getRecipeById(recipeId)
                if (recipe != null) {
                    repository.deleteRecipe(recipe)
                }
            }
            onFinished()
        }
    }

    fun saveRecipe(
        onFinished: () -> Unit
    ) {

        viewModelScope.launch {

            val savedRecipeId =

                repository.insertRecipe(

                    Recipe(

                        id =
                            recipeId ?: 0,

                        name = name.value,

                        style = style.value,

                        description =
                            description.value,

                        batchSize =
                            batchSize.value
                                .toDoubleOrNull()
                                ?: 20.0,

                        abv =
                            abv.value
                                .toDoubleOrNull(),

                        imageUri =
                            imageUri.value,

                        ingredients = ingredients.value
                    )
                )

            repository.clearIngredients(savedRecipeId)
            ingredients.value.forEach { ingredient ->
                repository.insertIngredient(ingredient, savedRecipeId)
            }

            repository.clearRecipeSteps(savedRecipeId)
            steps.value.forEachIndexed {

                    index,
                    step ->

                repository.insertRecipeStep(

                    RecipeStep(

                        id = step.id,

                        recipeId =
                            savedRecipeId,

                        title =
                            step.title,

                        description =
                            step.description,

                        durationMinutes =
                            step.durationMinutes,

                        stepOrder = index
                    )
                )
            }

            onFinished()
        }
    }
}