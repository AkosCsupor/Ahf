package hu.bme.aut.android.brewbuddy.domain.repository

import hu.bme.aut.android.brewbuddy.domain.model.process.BrewProcess
import hu.bme.aut.android.brewbuddy.domain.model.process.BrewStep
import kotlinx.coroutines.flow.Flow

interface BrewProcessRepository {

    fun observeProcessById(
        processId: Long
    ): Flow<BrewProcess?>

    fun observeActiveProcesses():
            Flow<List<BrewProcess>>

    fun observeFinishedProcesses():
            Flow<List<BrewProcess>>

    fun observeStepsForProcess(
        processId: Long
    ): Flow<List<BrewStep>>

    suspend fun createProcessFromRecipe(

        recipeId: Long,

        recipeName: String,

        steps: List<BrewStep>
    )

    suspend fun completeStep(
        stepId: Long
    )

    suspend fun finishProcess(
        processId: Long
    )

    suspend fun updateCurrentStep(
        processId: Long,
        stepIndex: Int,
        startedAt: Long
    )
}