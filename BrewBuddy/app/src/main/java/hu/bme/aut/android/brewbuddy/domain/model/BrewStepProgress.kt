package hu.bme.aut.android.brewbuddy.domain.model

data class BrewStepProgress(

    val id: Long,

    val processId: Long,

    val recipeStepId: Long,

    val title: String,

    val description: String,

    val durationMinutes: Int,

    val stepOrder: Int,

    val isCompleted: Boolean
)