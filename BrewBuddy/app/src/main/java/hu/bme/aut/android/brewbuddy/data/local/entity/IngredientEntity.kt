package hu.bme.aut.android.brewbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "ingredients"
)
data class IngredientEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val recipeId: Long = 0,

    val name: String,

    val amount: Double,

    val unit: String,

    val notes: String?
)