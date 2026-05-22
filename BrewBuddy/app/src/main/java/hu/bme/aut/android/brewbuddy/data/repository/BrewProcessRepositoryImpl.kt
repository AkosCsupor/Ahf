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

        brewProcessDao.insertProcess(

            BrewProcessEntity(

                recipeId = recipeId,

                recipeName = recipeName,

                startedAt =
                    System.currentTimeMillis(),

                completed = false
            )
        )

        steps.forEach { step ->

            brewStepDao.insertStep(

                BrewStepEntity(

                    processId = 0,

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
}