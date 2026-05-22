package hu.bme.aut.android.brewbuddy.presentation.recipe.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.brewbuddy.domain.model.Recipe
import hu.bme.aut.android.brewbuddy.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())

    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    init {

        loadRecipes()
    }

    private fun loadRecipes() {

        repository.observeRecipes()
            .onEach {

                _recipes.value = it
            }
            .launchIn(viewModelScope)
    }
    fun insertRecipe(recipe: Recipe) {

        viewModelScope.launch {

            repository.insertRecipe(recipe)
        }
    }
}