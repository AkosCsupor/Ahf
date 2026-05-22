package hu.bme.aut.android.brewbuddy.domain.model

data class InventoryItem(

    val id: Long,

    val name: String,

    val quantity: Double,

    val unit: String
)