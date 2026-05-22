package hu.bme.aut.android.brewbuddy.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.brewbuddy.domain.model.Recipe

@Composable
fun RecipeCard(

    recipe: Recipe,

    onClick: () -> Unit
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clickable {

                onClick()
            }
    ) {

        Column(

            modifier = Modifier
                .padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            Text(

                text = recipe.name,

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall
            )

            Text(

                text = recipe.style,

                style =
                    MaterialTheme
                        .typography
                        .bodyLarge
            )

            Text(

                text =
                    "Batch Size: ${recipe.batchSize} L"
            )

            recipe.abv?.let {

                Text(
                    text = "ABV: $it%"
                )
            }
        }
    }
}