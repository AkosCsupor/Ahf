package hu.bme.aut.android.brewbuddy.domain.model

data class Recipe(

    val id: Long,

    val name: String,

    val style: String,

    val description: String,

    val batchSize: Double,

    val og: Double? = null,

    val fg: Double? = null,

    val ibu: Int? = null,

    val abv: Double? = null,

    val notes: String? = null
)