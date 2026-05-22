package hu.bme.aut.android.brewbuddy.domain.model

data class RecipeStep(

    val id: Long,

    val recipeId: Long,

    val title: String,

    val description: String,

    val durationMinutes: Int,

    val stepOrder: Int
)