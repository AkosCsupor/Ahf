package hu.bme.aut.android.brewbuddy.worker

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object BrewTimerScheduler {

    fun scheduleTimer(

        context: Context,

        stepTitle: String,

        durationMinutes: Int
    ) {

        val data =
            Data.Builder()
                .putString(
                    "stepTitle",
                    stepTitle
                )
                .build()

        val request =
            OneTimeWorkRequestBuilder<
                    BrewTimerWorker>()
                .setInitialDelay(
                    durationMinutes.toLong(),
                    TimeUnit.MINUTES
                )
                .setInputData(data)
                .build()

        WorkManager
            .getInstance(context)
            .enqueue(request)
    }
}