package hu.bme.aut.android.brewbuddy.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.brewbuddy.data.repository.BrewProcessRepositoryImpl
import hu.bme.aut.android.brewbuddy.data.repository.RecipeRepositoryImpl
import hu.bme.aut.android.brewbuddy.domain.repository.BrewProcessRepository
import hu.bme.aut.android.brewbuddy.domain.repository.RecipeRepository
import javax.inject.Singleton
import hu.bme.aut.android.brewbuddy.data.repository.InventoryRepositoryImpl
import hu.bme.aut.android.brewbuddy.domain.repository.InventoryRepository
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRecipeRepository(
        impl: RecipeRepositoryImpl
    ): RecipeRepository

    @Binds
    @Singleton
    abstract fun bindBrewProcessRepository(
        impl: BrewProcessRepositoryImpl
    ): BrewProcessRepository
    @Binds
    @Singleton
    abstract fun bindInventoryRepository(
        impl: InventoryRepositoryImpl
    ): InventoryRepository


}