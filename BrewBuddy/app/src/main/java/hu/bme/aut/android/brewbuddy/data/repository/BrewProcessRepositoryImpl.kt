package hu.bme.aut.android.brewbuddy.data.repository

import hu.bme.aut.android.brewbuddy.data.local.dao.BrewProcessDao
import hu.bme.aut.android.brewbuddy.data.local.dao.BrewStepDao
import hu.bme.aut.android.brewbuddy.data.local.entity.BrewProcessEntity
import hu.bme.aut.android.brewbuddy.data.local.entity.BrewStepEntity
import hu.bme.aut.android.brewbuddy.domain.model.process.BrewProcess
import hu.bme.aut.android.brewbuddy.domain.model.process.BrewStep
import hu.bme.aut.android.brewbuddy.domain.repository.BrewProcessRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BrewProcessRepositoryImpl @Inject constructor(

    private val brewProcessDao:
    BrewProcessDao,

    private val brewStepDao:
    BrewStepDao

) : BrewProcessRepository {

    override fun observeProcessById(
        processId: Long
    ): Flow<BrewProcess?> {

        return brewProcessDao
            .observeProcessById(processId)
            .map { entity ->

                entity?.let {

                    BrewProcess(

                        id = it.id,

                        recipeId = it.recipeId,

                        recipeName = it.recipeName,

                        startedAt = it.startedAt,

                        currentStepIndex =
                            it.currentStepIndex,

                        currentStepStartedAt =
                            it.currentStepStartedAt,

                        isCompleted =
                            it.isCompleted,

                        fermentationEndTime =
                            it.fermentationEndTime
                    )
                }
            }
    }

    override fun observeActiveProcesses():
            Flow<List<BrewProcess>> {

        return brewProcessDao
            .observeActiveProcesses()
            .map { entities ->

                entities.map { entity ->

                    BrewProcess(

                        id = entity.id,

                        recipeId =
                            entity.recipeId,

                        recipeName =
                            entity.recipeName,

                        startedAt =
                            entity.startedAt,

                        currentStepIndex =
                            entity.currentStepIndex,

                        currentStepStartedAt =
                            entity.currentStepStartedAt,

                        isCompleted =
                            entity.isCompleted,

                        fermentationEndTime =
                            entity.fermentationEndTime
                    )
                }
            }
    }

    override fun observeFinishedProcesses():
            Flow<List<BrewProcess>> {

        return brewProcessDao
            .observeFinishedProcesses()
            .map { entities ->

                entities.map { entity ->

                    BrewProcess(

                        id = entity.id,

                        recipeId =
                            entity.recipeId,

                        recipeName =
                            entity.recipeName,

                        startedAt =
                            entity.startedAt,

                        currentStepIndex =
                            entity.currentStepIndex,

                        currentStepStartedAt =
                            entity.currentStepStartedAt,

                        isCompleted =
                            entity.isCompleted,

                        fermentationEndTime =
                            entity.fermentationEndTime
                    )
                }
            }
    }

    override fun observeStepsForProcess(
        processId: Long
    ): Flow<List<BrewStep>> {

        return brewStepDao
            .observeStepsForProcess(processId)
            .map { entities ->

                entities.map { entity ->

                    BrewStep(

                        id = entity.id,

                        processId =
                            entity.processId,

                        title = entity.title,

                        description =
                            entity.description,

                        durationMinutes =
                            entity.durationMinutes,

                        stepOrder =
                            entity.stepOrder,

                        completed =
                            entity.completed
                    )
                }
            }
    }

    override suspend fun createProcessFromRecipe(

        recipeId: Long,

        recipeName: String,

        steps: List<BrewStep>
    ) {

        val processId =
            brewProcessDao.insertProcess(

            BrewProcessEntity(

                recipeId = recipeId,

                recipeName = recipeName,

                startedAt =
                    System.currentTimeMillis(),

                currentStepIndex = 0,

                currentStepStartedAt =
                    System.currentTimeMillis(),

                isCompleted = false,

                fermentationEndTime = null
            )
            )

        steps.forEach { step ->

            brewStepDao.insertStep(

                BrewStepEntity(

                    processId = processId,

                    title = step.title,

                    description =
                        step.description,

                    durationMinutes =
                        step.durationMinutes,

                    stepOrder =
                        step.stepOrder,

                    completed = false
                )
            )
        }
    }

    override suspend fun completeStep(
        stepId: Long
    ) {

        brewStepDao.completeStep(stepId)
    }

    override suspend fun finishProcess(
        processId: Long
    ) {

        brewProcessDao.finishProcess(processId)
    }

    override suspend fun updateCurrentStep(
        processId: Long,
        stepIndex: Int,
        startedAt: Long
    ) {

        brewProcessDao.updateCurrentStep(
            processId = processId,
            stepIndex = stepIndex,
            startedAt = startedAt
        )
    }
}