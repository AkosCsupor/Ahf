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
        WHERE completed = 0
        """
    )
    fun observeActiveProcesses():
            Flow<List<BrewProcessEntity>>

    @Insert(
        onConflict =
            OnConflictStrategy.REPLACE
    )
    suspend fun insertProcess(
        process: BrewProcessEntity
    )
}