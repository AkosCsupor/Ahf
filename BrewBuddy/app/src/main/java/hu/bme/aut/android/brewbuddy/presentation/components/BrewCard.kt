package hu.bme.aut.android.brewbuddy.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BrewCard(

    title: String,

    subtitle: String,

    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,

        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme
                    .colorScheme
                    .surface
        )
    ) {

        Column(
            modifier =
                Modifier.padding(16.dp)
        ) {

            Text(
                text = title,

                style =
                    MaterialTheme
                        .typography
                        .titleLarge
            )

            Text(
                text = subtitle,

                style =
                    MaterialTheme
                        .typography
                        .bodyMedium
            )
        }
    }
}