package hu.bme.aut.android.brewbuddy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.brewbuddy.data.local.entity.BrewHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BrewHistoryDao {

    @Query("SELECT * FROM brew_history")
    fun getBrewHistory():
            Flow<List<BrewHistoryEntity>>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertBrewHistory(
        brewHistory: BrewHistoryEntity
    )
}