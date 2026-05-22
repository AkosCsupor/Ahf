package hu.bme.aut.android.brewbuddy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.brewbuddy.data.local.entity.RecipeStepEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeStepDao {

    @Query(
        """
        SELECT * FROM recipe_steps
        WHERE recipeId = :recipeId
        ORDER BY stepOrder ASC
        """
    )
    fun observeStepsForRecipe(
        recipeId: Long
    ): Flow<List<RecipeStepEntity>>

    @Insert(
        onConflict =
            OnConflictStrategy.REPLACE
    )
    suspend fun insertStep(
        step: RecipeStepEntity
    )

    @Query(
        """
        DELETE FROM recipe_steps
        WHERE id = :stepId
        """
    )
    suspend fun deleteStep(
        stepId: Long
    )
}