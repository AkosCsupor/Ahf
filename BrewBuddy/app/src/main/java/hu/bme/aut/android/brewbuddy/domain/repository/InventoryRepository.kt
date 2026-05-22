package hu.bme.aut.android.brewbuddy.domain.repository

import hu.bme.aut.android.brewbuddy.domain.model.Ingredient
import kotlinx.coroutines.flow.Flow

interface InventoryRepository {

    fun observeIngredients():
            Flow<List<Ingredient>>

    suspend fun getIngredientById(
        id: Long
    ): Ingredient?

    suspend fun insertIngredient(
        ingredient: Ingredient
    )

    suspend fun deleteIngredient(
        ingredient: Ingredient
    )
}