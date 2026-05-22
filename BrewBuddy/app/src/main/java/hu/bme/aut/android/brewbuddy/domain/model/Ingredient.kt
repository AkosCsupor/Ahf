package hu.bme.aut.android.brewbuddy.domain.model

data class Ingredient(

    val id: Long,

    val name: String,

    val amount: Double,

    val unit: String,

    val notes: String?
)