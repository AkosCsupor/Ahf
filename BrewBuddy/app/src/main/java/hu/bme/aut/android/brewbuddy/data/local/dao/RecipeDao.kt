package hu.bme.aut.android.brewbuddy.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.brewbuddy.data.local.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipes")
    fun getAllRecipes():
            Flow<List<RecipeEntity>>

    @Query(
        "SELECT * FROM recipes WHERE id = :id"
    )
    suspend fun getRecipeById(
        id: Long
    ): RecipeEntity?

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )

    @Query("SELECT * FROM recipes")
    fun observeRecipes():
            Flow<List<RecipeEntity>>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertRecipe(
        recipe: RecipeEntity
    ): Long

    @Delete
    suspend fun deleteRecipe(
        recipe: RecipeEntity
    )
}