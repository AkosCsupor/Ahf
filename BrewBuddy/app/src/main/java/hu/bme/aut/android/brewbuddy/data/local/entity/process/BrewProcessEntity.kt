package hu.bme.aut.android.brewbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "brew_processes"
)
data class BrewProcessEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val recipeId: Long,

    val recipeName: String,

    val startedAt: Long,

    val currentStepIndex: Int,

    val isCompleted: Boolean,

    val fermentationEndTime: Long?
)