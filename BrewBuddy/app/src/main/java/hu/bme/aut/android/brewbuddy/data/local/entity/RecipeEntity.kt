package hu.bme.aut.android.brewbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    val style: String,

    val batchSize: Double,

    val description: String,

    val imageUri: String? = null,

    val abv: Double? = null
)