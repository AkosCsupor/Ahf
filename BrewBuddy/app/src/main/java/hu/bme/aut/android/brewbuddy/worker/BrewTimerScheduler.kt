package hu.bme.aut.android.brewbuddy.worker

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object BrewTimerScheduler {

    fun scheduleTimer(

        context: Context,

        processId: Long,

        stepId: Long,

        stepTitle: String,

        delaySeconds: Int
    ) {

        val safeDelay =
            delaySeconds.coerceAtLeast(1)

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
                    safeDelay.toLong(),
                    TimeUnit.SECONDS
                )
                .setInputData(data)
                .build()

        WorkManager
            .getInstance(context)
            .enqueueUniqueWork(
                "brew_step_timer_${processId}_${stepId}",
                ExistingWorkPolicy.REPLACE,
                request
            )
    }
}