package hu.bme.aut.android.brewbuddy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.brewbuddy.data.local.entity.BrewProcessEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BrewProcessDao {

    @Query(
        """
        SELECT * FROM brew_processes
        WHERE isCompleted = 0
        ORDER BY startedAt DESC
        """
    )
    fun observeActiveProcesses():
            Flow<List<BrewProcessEntity>>

    @Query(
        """
        SELECT * FROM brew_processes
        WHERE isCompleted = 1
        ORDER BY startedAt DESC
        LIMIT 10
        """
    )
    fun observeFinishedProcesses():
            Flow<List<BrewProcessEntity>>

    @Query(
        """
        SELECT * FROM brew_processes
        WHERE id = :processId
        """
    )
    fun observeProcessById(
        processId: Long
    ): Flow<BrewProcessEntity?>

    @Insert(
        onConflict =
            OnConflictStrategy.REPLACE
    )
    suspend fun insertProcess(
        process: BrewProcessEntity
    ): Long

    @Query(
        """
        UPDATE brew_processes
        SET isCompleted = 1
        WHERE id = :processId
        """
    )
    suspend fun finishProcess(
        processId: Long
    )

    @Query(
        """
        UPDATE brew_processes
        SET currentStepIndex = :stepIndex,
            currentStepStartedAt = :startedAt
        WHERE id = :processId
        """
    )
    suspend fun updateCurrentStep(
        processId: Long,
        stepIndex: Int,
        startedAt: Long
    )
}