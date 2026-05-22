package hu.bme.aut.android.brewbuddy.data.repository

import hu.bme.aut.android.brewbuddy.data.local.dao.RecipeDao
import hu.bme.aut.android.brewbuddy.data.local.dao.RecipeStepDao
import hu.bme.aut.android.brewbuddy.data.local.dao.IngredientDao
import hu.bme.aut.android.brewbuddy.data.local.dao.BrewHistoryDao
import hu.bme.aut.android.brewbuddy.data.local.entity.BrewHistoryEntity
import hu.bme.aut.android.brewbuddy.data.local.entity.IngredientEntity
import hu.bme.aut.android.brewbuddy.data.local.entity.RecipeEntity
import hu.bme.aut.android.brewbuddy.data.local.entity.RecipeStepEntity
import hu.bme.aut.android.brewbuddy.domain.model.BrewHistory
import hu.bme.aut.android.brewbuddy.domain.model.Recipe
import hu.bme.aut.android.brewbuddy.domain.model.RecipeStep
import hu.bme.aut.android.brewbuddy.domain.model.Ingredient
import hu.bme.aut.android.brewbuddy.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(

    private val recipeDao: RecipeDao,

    private val recipeStepDao:
    RecipeStepDao,

    private val ingredientDao:
    IngredientDao,

    private val brewHistoryDao:
    BrewHistoryDao

) : RecipeRepository {

    override fun observeRecipes():
            Flow<List<Recipe>> {

        return recipeDao
            .observeRecipesWithIngredients()
            .map { entities ->

                entities.map {

                    Recipe(

                        id = it.recipe.id,

                        name = it.recipe.name,

                        style = it.recipe.style,

                        description =
                            it.recipe.description,

                        batchSize =
                            it.recipe.batchSize,

                        abv = it.recipe.abv,

                        imageUri = it.recipe.imageUri,

                        ingredients = it.ingredients.map { ingredient ->
                            Ingredient(
                                id = ingredient.id,
                                name = ingredient.name,
                                amount = ingredient.amount,
                                unit = ingredient.unit,
                                notes = ingredient.notes
                            )
                        }
                    )
                }
            }
    }

    override suspend fun getRecipeById(
        id: Long
    ): Recipe? {

        return recipeDao
            .getRecipeWithIngredientsById(id)
            ?.let {

                Recipe(

                    id = it.recipe.id,

                    name = it.recipe.name,

                    style = it.recipe.style,

                    description =
                        it.recipe.description,

                    batchSize =
                        it.recipe.batchSize,

                    abv = it.recipe.abv,

                    imageUri = it.recipe.imageUri,

                    ingredients = it.ingredients.map { ingredient ->
                        Ingredient(
                            id = ingredient.id,
                            name = ingredient.name,
                            amount = ingredient.amount,
                            unit = ingredient.unit,
                            notes = ingredient.notes
                        )
                    }
                )
            }
    }

    override suspend fun insertRecipe(
        recipe: Recipe
    ): Long {

        return recipeDao.insertRecipe(

            RecipeEntity(

                id = recipe.id,

                name = recipe.name,

                style = recipe.style,

                description =
                    recipe.description,

                batchSize =
                    recipe.batchSize,

                imageUri = recipe.imageUri,

                abv = recipe.abv
            )
        )
    }

    override suspend fun deleteRecipe(
        recipe: Recipe
    ) {

        recipeDao.deleteRecipe(

            RecipeEntity(

                id = recipe.id,

                name = recipe.name,

                style = recipe.style,

                description =
                    recipe.description,

                batchSize =
                    recipe.batchSize,

                imageUri = recipe.imageUri,

                abv = recipe.abv
            )
        )
    }

    override fun observeRecipeSteps(
        recipeId: Long
    ): Flow<List<RecipeStep>> {

        return recipeStepDao
            .observeStepsForRecipe(
                recipeId
            )
            .map { entities ->

                entities.map {

                    RecipeStep(

                        id = it.id,

                        recipeId =
                            it.recipeId,

                        title = it.title,

                        description =
                            it.description,

                        durationMinutes =
                            it.durationMinutes,

                        stepOrder =
                            it.stepOrder
                    )
                }
            }
    }

    override suspend fun insertRecipeStep(
        step: RecipeStep
    ) {

        recipeStepDao.insertStep(

            RecipeStepEntity(

                id = step.id,

                recipeId =
                    step.recipeId,

                title = step.title,

                description =
                    step.description,

                durationMinutes =
                    step.durationMinutes,

                stepOrder =
                    step.stepOrder
            )
        )
    }

    override suspend fun deleteRecipeStep(
        stepId: Long
    ) {

        recipeStepDao.deleteStep(
            stepId
        )
    }

    override suspend fun clearRecipeSteps(recipeId: Long) {
        recipeStepDao.deleteStepsForRecipe(recipeId)
    }

    override fun observeIngredients(recipeId: Long): Flow<List<Ingredient>> {
        return ingredientDao.observeIngredientsForRecipe(recipeId).map { entities ->
            entities.map {
                Ingredient(
                    id = it.id,
                    name = it.name,
                    amount = it.amount,
                    unit = it.unit,
                    notes = it.notes
                )
            }
        }
    }

    override suspend fun insertIngredient(ingredient: Ingredient, recipeId: Long) {
        ingredientDao.insertIngredient(
            IngredientEntity(
                id = ingredient.id,
                recipeId = recipeId,
                name = ingredient.name,
                amount = ingredient.amount,
                unit = ingredient.unit,
                notes = ingredient.notes
            )
        )
    }

    override suspend fun deleteIngredient(ingredient: Ingredient) {
        ingredientDao.deleteIngredientById(ingredient.id)
    }

    override suspend fun clearIngredients(recipeId: Long) {
        ingredientDao.deleteIngredientsForRecipe(recipeId)
    }

    override fun getBrewHistory():
            Flow<List<BrewHistory>> {

        return brewHistoryDao
            .getBrewHistory()
            .map { entities ->

                entities.map {

                    BrewHistory(

                        id = it.id,

                        recipeName = it.recipeName,

                        brewDate = it.brewDate,

                        abv = it.abv,

                        notes = it.notes
                    )
                }
            }
    }

    override suspend fun insertBrewHistory(
        brewHistory: BrewHistory
    ) {

        brewHistoryDao.insertBrewHistory(

            BrewHistoryEntity(

                id = brewHistory.id,

                recipeName = brewHistory.recipeName,

                brewDate = brewHistory.brewDate,

                abv = brewHistory.abv,

                notes = brewHistory.notes
            )
        )
    }
}