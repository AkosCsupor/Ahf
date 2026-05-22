package hu.bme.aut.android.brewbuddy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import hu.bme.aut.android.brewbuddy.notifications.NotificationHelper

@HiltAndroidApp
class BrewBuddyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Az értesítési csatorna létrehozása az app indulásakor
        NotificationHelper.createChannel(this)
    }
}
