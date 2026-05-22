package hu.bme.aut.android.brewbuddy.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.brewbuddy.data.local.AppDatabase
import hu.bme.aut.android.brewbuddy.data.local.dao.BrewProcessDao
import hu.bme.aut.android.brewbuddy.data.local.dao.BrewStepDao
import hu.bme.aut.android.brewbuddy.data.local.dao.IngredientDao
import hu.bme.aut.android.brewbuddy.data.local.dao.RecipeDao
import hu.bme.aut.android.brewbuddy.data.local.dao.RecipeStepDao
import hu.bme.aut.android.brewbuddy.data.repository.BrewProcessRepositoryImpl
import hu.bme.aut.android.brewbuddy.data.repository.InventoryRepositoryImpl
import hu.bme.aut.android.brewbuddy.data.repository.RecipeRepositoryImpl
import hu.bme.aut.android.brewbuddy.domain.repository.BrewProcessRepository
import hu.bme.aut.android.brewbuddy.domain.repository.InventoryRepository
import hu.bme.aut.android.brewbuddy.domain.repository.RecipeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context:
        Context
    ): AppDatabase {

        return Room.databaseBuilder(

            context,

            AppDatabase::class.java,

            "brewbuddy_database"
        ).build()
    }

    @Provides
    fun provideRecipeDao(
        database: AppDatabase
    ): RecipeDao {

        return database.recipeDao()
    }

    @Provides
    fun provideRecipeStepDao(
        database: AppDatabase
    ): RecipeStepDao {

        return database.recipeStepDao()
    }

    @Provides
    fun provideIngredientDao(
        database: AppDatabase
    ): IngredientDao {

        return database.ingredientDao()
    }

    @Provides
    fun provideBrewProcessDao(
        database: AppDatabase
    ): BrewProcessDao {

        return database.brewProcessDao()
    }

    @Provides
    fun provideBrewStepDao(
        database: AppDatabase
    ): BrewStepDao {

        return database.brewStepDao()
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(

        recipeDao: RecipeDao,

        recipeStepDao: RecipeStepDao

    ): RecipeRepository {

        return RecipeRepositoryImpl(

            recipeDao,

            recipeStepDao
        )
    }

    @Provides
    @Singleton
    fun provideInventoryRepository(

        ingredientDao: IngredientDao

    ): InventoryRepository {

        return InventoryRepositoryImpl(
            ingredientDao
        )
    }

    @Provides
    @Singleton
    fun provideBrewProcessRepository(

        brewProcessDao:
        BrewProcessDao,

        brewStepDao:
        BrewStepDao

    ): BrewProcessRepository {

        return BrewProcessRepositoryImpl(

            brewProcessDao,

            brewStepDao
        )
    }
}