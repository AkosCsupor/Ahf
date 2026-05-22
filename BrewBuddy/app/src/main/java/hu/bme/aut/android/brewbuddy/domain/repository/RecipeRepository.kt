package hu.bme.aut.android.brewbuddy.domain.repository

import hu.bme.aut.android.brewbuddy.domain.model.Recipe
import hu.bme.aut.android.brewbuddy.domain.model.RecipeStep
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    fun observeRecipes():
            Flow<List<Recipe>>

    suspend fun getRecipeById(
        id: Long
    ): Recipe?

    suspend fun insertRecipe(
        recipe: Recipe
    ): Long

    suspend fun deleteRecipe(
        recipe: Recipe
    )

    fun observeRecipeSteps(
        recipeId: Long
    ): Flow<List<RecipeStep>>

    suspend fun insertRecipeStep(
        step: RecipeStep
    )

    suspend fun deleteRecipeStep(
        stepId: Long
    )
}