package hu.bme.aut.android.brewbuddy.data.repository

import hu.bme.aut.android.brewbuddy.data.local.dao.IngredientDao
import hu.bme.aut.android.brewbuddy.data.local.entity.IngredientEntity
import hu.bme.aut.android.brewbuddy.domain.model.Ingredient
import hu.bme.aut.android.brewbuddy.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(

    private val ingredientDao:
    IngredientDao

) : InventoryRepository {

    override fun observeIngredients():
            Flow<List<Ingredient>> {

        return ingredientDao
            .observeIngredients()
            .map { entities ->

                entities.map { entity ->

                    Ingredient(

                        id = entity.id,

                        name = entity.name,

                        amount =
                            entity.amount,

                        unit = entity.unit,

                        notes = entity.notes
                    )
                }
            }
    }

    override suspend fun getIngredientById(
        id: Long
    ): Ingredient? {

        val entity =
            ingredientDao
                .getIngredientById(id)
                ?: return null

        return Ingredient(

            id = entity.id,

            name = entity.name,

            amount = entity.amount,

            unit = entity.unit,

            notes = entity.notes
        )
    }

    override suspend fun insertIngredient(
        ingredient: Ingredient
    ) {

        ingredientDao.insertIngredient(

            IngredientEntity(

                id = ingredient.id,

                name = ingredient.name,

                amount =
                    ingredient.amount,

                unit = ingredient.unit,

                notes = ingredient.notes
            )
        )
    }

    override suspend fun deleteIngredient(
        ingredient: Ingredient
    ) {

        ingredientDao.deleteIngredient(

            IngredientEntity(

                id = ingredient.id,

                name = ingredient.name,

                amount =
                    ingredient.amount,

                unit = ingredient.unit,

                notes = ingredient.notes
            )
        )
    }
}