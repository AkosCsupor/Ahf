package hu.bme.aut.android.brewbuddy.domain.repository

import hu.bme.aut.android.brewbuddy.domain.model.process.BrewProcess
import hu.bme.aut.android.brewbuddy.domain.model.process.BrewStep
import kotlinx.coroutines.flow.Flow

interface BrewProcessRepository {

    fun observeActiveProcesses():
            Flow<List<BrewProcess>>

    suspend fun createProcessFromRecipe(

        recipeId: Long,

        recipeName: String,

        steps: List<BrewStep>
    )
}