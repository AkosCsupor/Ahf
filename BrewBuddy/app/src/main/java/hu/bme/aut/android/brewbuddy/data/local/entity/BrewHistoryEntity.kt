package hu.bme.aut.android.brewbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "brew_history")
data class BrewHistoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val recipeName: String,

    val brewDate: String,

    val abv: Double,

    val notes: String
)