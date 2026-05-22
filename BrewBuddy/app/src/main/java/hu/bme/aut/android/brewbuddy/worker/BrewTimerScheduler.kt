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
        delaySeconds: Long
    ) {
        if (delaySeconds <= 0) return

        val data = Data.Builder()
            .putString("stepTitle", stepTitle)
            .putLong("processId", processId)
            .putLong("stepId", stepId)
            .build()

        val request = OneTimeWorkRequestBuilder<BrewTimerWorker>()
            .setInitialDelay(delaySeconds, TimeUnit.SECONDS)
            .setInputData(data)
            .addTag("brew_timer")
            .build()

        WorkManager
            .getInstance(context)
            .enqueueUniqueWork(
                "brew_step_timer_${processId}_${stepId}",
                ExistingWorkPolicy.REPLACE,
                request
            )
    }

    fun cancelTimer(context: Context, processId: Long, stepId: Long) {
        WorkManager
            .getInstance(context)
            .cancelUniqueWork("brew_step_timer_${processId}_${stepId}")
    }
    
    fun cancelAllTimers(context: Context) {
        WorkManager.getInstance(context).cancelAllWorkByTag("brew_timer")
    }
}
