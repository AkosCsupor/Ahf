package hu.bme.aut.android.brewbuddy.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import hu.bme.aut.android.brewbuddy.R

class BrewNotificationWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {

        createNotificationChannel()

        val builder = NotificationCompat.Builder(
            applicationContext,
            "brew_channel"
        )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("BrewBuddy")
            .setContentText(
                "Fermentation is complete!"
            )
            .setPriority(
                NotificationCompat.PRIORITY_HIGH
            )

        if (
            ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return Result.failure()
        }

        NotificationManagerCompat
            .from(applicationContext)
            .notify(1, builder.build())

        return Result.success()
    }

    private fun createNotificationChannel() {

        val channel = NotificationChannel(
            "brew_channel",
            "Brew Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )

        val manager =
            applicationContext.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        manager.createNotificationChannel(channel)
    }
}