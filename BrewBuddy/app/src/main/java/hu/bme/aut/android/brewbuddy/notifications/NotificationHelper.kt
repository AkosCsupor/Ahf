package hu.bme.aut.android.brewbuddy.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import hu.bme.aut.android.brewbuddy.R

object NotificationHelper {

    const val CHANNEL_ID =
        "brew_timer_channel"

    fun createChannel(
        context: Context
    ) {

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

            val channel =
                NotificationChannel(

                    CHANNEL_ID,

                    "Brew Timer",

                    NotificationManager
                        .IMPORTANCE_HIGH
                )

            val manager =
                context.getSystemService(
                    NotificationManager::class.java
                )

            manager.createNotificationChannel(
                channel
            )
        }
    }

    fun showTimerFinishedNotification(

        context: Context,

        stepTitle: String
    ) {

        val notification =
            NotificationCompat.Builder(
                context,
                CHANNEL_ID
            )
                .setContentTitle(
                    "Brewing Step Finished"
                )
                .setContentText(
                    "$stepTitle completed"
                )
                .setSmallIcon(
                    R.mipmap.ic_launcher
                )
                .build()

        val manager =
            context.getSystemService(
                NotificationManager::class.java
            )

        manager.notify(
            stepTitle.hashCode(),
            notification
        )
    }
}