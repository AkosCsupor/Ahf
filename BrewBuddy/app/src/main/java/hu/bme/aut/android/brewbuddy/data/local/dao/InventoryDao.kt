package hu.bme.aut.android.brewbuddy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.brewbuddy.data.local.entity.InventoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @Query("SELECT * FROM inventory")
    fun observeInventoryItems():
            Flow<List<InventoryEntity>>

    @Query("SELECT * FROM inventory")
    suspend fun getAllInventoryItems():
            List<InventoryEntity>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertInventoryItem(
        item: InventoryEntity
    )
}