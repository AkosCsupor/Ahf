package hu.bme.aut.android.brewbuddy.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.brewbuddy.data.local.entity.IngredientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {

    @Query(
        """
        SELECT * FROM ingredients
        WHERE recipeId = 0
        ORDER BY name ASC
        """
    )
    fun observeIngredients():
            Flow<List<IngredientEntity>>

    @Query(
        """
        SELECT * FROM ingredients
        WHERE id = :id
        """
    )
    suspend fun getIngredientById(
        id: Long
    ): IngredientEntity?

    @Query("SELECT * FROM ingredients WHERE recipeId = :recipeId")
    fun observeIngredientsForRecipe(recipeId: Long): Flow<List<IngredientEntity>>

    @Insert(
        onConflict =
            OnConflictStrategy.REPLACE
    )
    suspend fun insertIngredient(
        ingredient: IngredientEntity
    )

    @Delete
    suspend fun deleteIngredient(
        ingredient: IngredientEntity
    )

    @Query("DELETE FROM ingredients WHERE id = :id")
    suspend fun deleteIngredientById(id: Long)

    @Query("DELETE FROM ingredients WHERE recipeId = :recipeId")
    suspend fun deleteIngredientsForRecipe(recipeId: Long)
}