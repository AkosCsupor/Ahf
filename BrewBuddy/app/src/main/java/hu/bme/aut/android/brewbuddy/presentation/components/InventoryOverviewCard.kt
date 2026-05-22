package hu.bme.aut.android.brewbuddy.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InventoryOverviewCard(

    totalItems: Int
) {

    Card {

        Column(

            modifier = Modifier
                .padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            Text(

                text = "Inventory Overview",

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall
            )

            Text(

                text =
                    "Total Items: $totalItems",

                style =
                    MaterialTheme
                        .typography
                        .bodyLarge
            )
        }
    }
}