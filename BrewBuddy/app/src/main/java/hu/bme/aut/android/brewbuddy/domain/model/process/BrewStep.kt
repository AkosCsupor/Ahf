package hu.bme.aut.android.brewbuddy.domain.model.process

data class BrewStep(

    val id: Long,

    val processId: Long,

    val title: String,

    val description: String,

    val durationMinutes: Int,

    val stepOrder: Int,

    val completed: Boolean
)