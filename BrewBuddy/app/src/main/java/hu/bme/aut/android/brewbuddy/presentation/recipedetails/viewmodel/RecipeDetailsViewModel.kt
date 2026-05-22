package hu.bme.aut.android.brewbuddy.presentation.recipedetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.brewbuddy.domain.model.Recipe
import hu.bme.aut.android.brewbuddy.domain.model.RecipeStep
import hu.bme.aut.android.brewbuddy.domain.model.process.BrewStep
import hu.bme.aut.android.brewbuddy.domain.repository.BrewProcessRepository
import hu.bme.aut.android.brewbuddy.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(

    private val recipeRepository:
    RecipeRepository,

    private val brewProcessRepository:
    BrewProcessRepository,

    savedStateHandle:
    SavedStateHandle

) : ViewModel() {

    private val recipeId: Long =
        checkNotNull(

            savedStateHandle["recipeId"]
        )

    private val _recipe =
        MutableStateFlow<Recipe?>(
            null
        )

    val recipe:
            StateFlow<Recipe?>
            = _recipe

    private val _steps =
        MutableStateFlow<List<RecipeStep>>(
            emptyList()
        )

    val steps:
            StateFlow<List<RecipeStep>>
            = _steps

    init {

        loadRecipe()

        observeSteps()
    }

    private fun loadRecipe() {

        viewModelScope.launch {

            _recipe.value =

                recipeRepository
                    .getRecipeById(
                        recipeId
                    )
        }
    }

    private fun observeSteps() {

        viewModelScope.launch {

            recipeRepository
                .observeRecipeSteps(
                    recipeId
                )
                .collect {

                    _steps.value = it
                }
        }
    }

    fun startBrewing() {

        viewModelScope.launch {

            val currentRecipe =
                recipe.value
                    ?: return@launch

            val brewSteps =

                steps.value.map {

                    BrewStep(

                        id = 0,

                        processId = 0,

                        title = it.title,

                        description =
                            it.description,

                        durationMinutes =
                            it.durationMinutes,

                        stepOrder =
                            it.stepOrder,

                        completed = false
                    )
                }

            brewProcessRepository
                .createProcessFromRecipe(

                    recipeId =
                        currentRecipe.id,

                    recipeName =
                        currentRecipe.name,

                    steps = brewSteps
                )
        }
    }

    fun deleteStep(
        stepId: Long
    ) {

        viewModelScope.launch {

            recipeRepository
                .deleteRecipeStep(
                    stepId
                )
        }
    }
}