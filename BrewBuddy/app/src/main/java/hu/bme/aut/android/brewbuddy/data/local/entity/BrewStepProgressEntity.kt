package hu.bme.aut.android.brewbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "brew_step_progress"
)
data class BrewStepProgressEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val processId: Long,

    val recipeStepId: Long,

    val title: String,

    val description: String,

    val durationMinutes: Int,

    val stepOrder: Int,

    val isCompleted: Boolean
)