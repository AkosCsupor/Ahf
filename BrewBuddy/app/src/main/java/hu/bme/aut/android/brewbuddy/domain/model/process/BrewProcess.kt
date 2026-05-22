package hu.bme.aut.android.brewbuddy.domain.model.process

data class BrewProcess(

    val id: Long,

    val recipeId: Long,

    val recipeName: String,

    val startedAt: Long,

    val currentStepIndex: Int,

    val currentStepStartedAt: Long,

    val isCompleted: Boolean,

    val fermentationEndTime: Long?
)