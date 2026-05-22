package hu.bme.aut.android.brewbuddy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.bme.aut.android.brewbuddy.data.local.dao.BrewProcessDao
import hu.bme.aut.android.brewbuddy.data.local.dao.BrewStepDao
import hu.bme.aut.android.brewbuddy.data.local.dao.BrewHistoryDao
import hu.bme.aut.android.brewbuddy.data.local.dao.IngredientDao
import hu.bme.aut.android.brewbuddy.data.local.dao.RecipeDao
import hu.bme.aut.android.brewbuddy.data.local.dao.RecipeStepDao
import hu.bme.aut.android.brewbuddy.data.local.entity.BrewHistoryEntity
import hu.bme.aut.android.brewbuddy.data.local.entity.BrewProcessEntity
import hu.bme.aut.android.brewbuddy.data.local.entity.BrewStepEntity
import hu.bme.aut.android.brewbuddy.data.local.entity.IngredientEntity
import hu.bme.aut.android.brewbuddy.data.local.entity.RecipeEntity
import hu.bme.aut.android.brewbuddy.data.local.entity.RecipeStepEntity

@Database(
    entities = [

        RecipeEntity::class,

        RecipeStepEntity::class,

        IngredientEntity::class,

        BrewHistoryEntity::class,

        BrewProcessEntity::class,

        BrewStepEntity::class
    ],

        version = 16,

    exportSchema = false
)
abstract class AppDatabase :
    RoomDatabase() {

    abstract fun recipeDao():
            RecipeDao

    abstract fun recipeStepDao():
            RecipeStepDao

    abstract fun ingredientDao():
            IngredientDao

    abstract fun brewProcessDao():
            BrewProcessDao

    abstract fun brewStepDao():
            BrewStepDao

    abstract fun brewHistoryDao():
            BrewHistoryDao
}