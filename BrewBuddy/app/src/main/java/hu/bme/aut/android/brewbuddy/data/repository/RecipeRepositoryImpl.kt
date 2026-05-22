package hu.bme.aut.android.brewbuddy.data.repository

import hu.bme.aut.android.brewbuddy.data.local.dao.RecipeDao
import hu.bme.aut.android.brewbuddy.data.local.dao.RecipeStepDao
import hu.bme.aut.android.brewbuddy.data.local.entity.RecipeEntity
import hu.bme.aut.android.brewbuddy.data.local.entity.RecipeStepEntity
import hu.bme.aut.android.brewbuddy.domain.model.Recipe
import hu.bme.aut.android.brewbuddy.domain.model.RecipeStep
import hu.bme.aut.android.brewbuddy.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(

    private val recipeDao: RecipeDao,

    private val recipeStepDao:
    RecipeStepDao

) : RecipeRepository {

    override fun observeRecipes():
            Flow<List<Recipe>> {

        return recipeDao
            .observeRecipes()
            .map { entities ->

                entities.map {

                    Recipe(

                        id = it.id,

                        name = it.name,

                        style = it.style,

                        description =
                            it.description,

                        batchSize =
                            it.batchSize,

                        abv = it.abv
                    )
                }
            }
    }

    override suspend fun getRecipeById(
        id: Long
    ): Recipe? {

        return recipeDao
            .getRecipeById(id)
            ?.let {

                Recipe(

                    id = it.id,

                    name = it.name,

                    style = it.style,

                    description =
                        it.description,

                    batchSize =
                        it.batchSize,

                    abv = it.abv
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
}