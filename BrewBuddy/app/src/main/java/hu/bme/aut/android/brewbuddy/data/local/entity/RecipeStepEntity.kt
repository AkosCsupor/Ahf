package hu.bme.aut.android.brewbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipe_steps"
)
data class RecipeStepEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val recipeId: Long,

    val title: String,

    val description: String,

    val durationMinutes: Int,

    val stepOrder: Int
)