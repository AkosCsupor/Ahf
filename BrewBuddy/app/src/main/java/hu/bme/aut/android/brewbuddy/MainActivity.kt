package hu.bme.aut.android.brewbuddy
import hu.bme.aut.android.brewbuddy.notifications.NotificationHelper
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.android.brewbuddy.navigation.NavGraph
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.brewbuddy.ui.theme.BrewBuddyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationHelper
            .createChannel(this)
        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS
                ),
                100
            )
        }
        enableEdgeToEdge()

        setContent {
            BrewBuddyRoot()
        }
    }
}

@Composable
fun BrewBuddyRoot() {
    BrewBuddyTheme {
        Surface(
            modifier = Modifier
        ) {
            val navController = rememberNavController()
            NavGraph(navController = navController)
        }
    }
}
