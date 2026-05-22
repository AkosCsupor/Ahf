package hu.bme.aut.android.brewbuddy.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardCard(

    title: String,

    description: String,

    onClick: () -> Unit
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable {

                onClick()
            }
    ) {

        Column(

            modifier = Modifier
                .padding(16.dp),

            verticalArrangement =
                Arrangement.Center,

            horizontalAlignment =
                Alignment.Start
        ) {

            Text(

                text = title,

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall
            )

            Text(

                text = description,

                style =
                    MaterialTheme
                        .typography
                        .bodyMedium
            )
        }
    }
}