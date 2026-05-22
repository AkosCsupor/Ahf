package hu.bme.aut.android.brewbuddy.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import hu.bme.aut.android.brewbuddy.data.local.entity.IngredientEntity
import hu.bme.aut.android.brewbuddy.data.local.entity.RecipeEntity

data class RecipeWithIngredients(

    @Embedded
    val recipe: RecipeEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val ingredients: List<IngredientEntity>
)