package hu.bme.aut.android.brewbuddy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.brewbuddy.data.local.entity.BrewStepEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BrewStepDao {

    @Query(
        """
        SELECT * FROM brew_steps
        WHERE processId = :processId
        ORDER BY stepOrder ASC
        """
    )
    fun observeStepsForProcess(
        processId: Long
    ): Flow<List<BrewStepEntity>>

    @Insert(
        onConflict =
            OnConflictStrategy.REPLACE
    )
    suspend fun insertStep(
        step: BrewStepEntity
    )
}