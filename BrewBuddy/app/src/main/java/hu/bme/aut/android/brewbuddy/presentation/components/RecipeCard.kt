package hu.bme.aut.android.brewbuddy.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import hu.bme.aut.android.brewbuddy.domain.model.Recipe

@Composable
fun RecipeCard(

    recipe: Recipe,

    onClick: () -> Unit
) {

    ElevatedCard(

        modifier = Modifier
            .fillMaxWidth()
            .clickable {

                onClick()
            },
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (!recipe.imageUri.isNullOrBlank()) {
                AsyncImage(
                    model = recipe.imageUri,
                    contentDescription = "Recipe Image",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Text(

                    text = recipe.name,

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(

                    text = recipe.style,

                    style =
                        MaterialTheme
                            .typography
                            .bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
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
}