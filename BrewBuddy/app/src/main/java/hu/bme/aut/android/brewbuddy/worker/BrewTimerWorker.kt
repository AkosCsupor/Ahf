package hu.bme.aut.android.brewbuddy.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import hu.bme.aut.android.brewbuddy.notifications.NotificationHelper

class BrewTimerWorker(

    context: Context,

    params: WorkerParameters

) : Worker(context, params) {

    override fun doWork():
            Result {

        val stepTitle =
            inputData.getString(
                "stepTitle"
            )
                ?: "Brewing Step"

        NotificationHelper
            .showTimerFinishedNotification(

                applicationContext,

                stepTitle
            )

        return Result.success()
    }
}