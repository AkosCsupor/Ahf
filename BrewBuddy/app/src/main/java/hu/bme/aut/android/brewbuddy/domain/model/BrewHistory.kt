package hu.bme.aut.android.brewbuddy.domain.model

data class BrewHistory(

    val id: Long,

    val recipeName: String,

    val brewDate: String,

    val abv: Double,

    val notes: String
)