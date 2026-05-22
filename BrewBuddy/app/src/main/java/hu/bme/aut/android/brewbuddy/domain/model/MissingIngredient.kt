package hu.bme.aut.android.brewbuddy.domain.model

data class MissingIngredient(

    val name: String,

    val missingAmount: Double,

    val unit: String
)