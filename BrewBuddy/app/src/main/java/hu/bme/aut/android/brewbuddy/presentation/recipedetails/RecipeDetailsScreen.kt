package hu.bme.aut.android.brewbuddy.presentation.recipedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import hu.bme.aut.android.brewbuddy.navigation.Routes
import hu.bme.aut.android.brewbuddy.presentation.recipedetails.viewmodel.RecipeDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(

    navController: NavController,

    viewModel:
    RecipeDetailsViewModel =
        hiltViewModel()
) {

    val recipe by
    viewModel.recipe
        .collectAsState()

    val steps by
    viewModel.steps
        .collectAsState()

    var showScalingDialog by remember { mutableStateOf(false) }

    if (showScalingDialog && recipe != null) {
        var targetBatchSizeStr by remember { mutableStateOf(recipe!!.batchSize.toString()) }
        val targetBatchSize = targetBatchSizeStr.toDoubleOrNull() ?: recipe!!.batchSize
        val scaleFactor = if (recipe!!.batchSize > 0) targetBatchSize / recipe!!.batchSize else 1.0

        AlertDialog(
            onDismissRequest = { showScalingDialog = false },
            title = { Text("Scale Recipe") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Original Batch Size: ${recipe!!.batchSize} L")
                    Text("Enter target batch size:", style = MaterialTheme.typography.bodySmall)
                    OutlinedTextField(
                        value = targetBatchSizeStr,
                        onValueChange = { targetBatchSizeStr = it },
                        label = { Text("Target L") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Scaled Ingredients:", style = MaterialTheme.typography.titleSmall)
                    recipe!!.ingredients.forEach { ingredient ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(ingredient.name, style = MaterialTheme.typography.bodyMedium)
                            Text(
                                "${"%.2f".format(ingredient.amount * scaleFactor)} ${ingredient.unit}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.startBrewing(targetBatchSize)
                        showScalingDialog = false
                        navController.navigate(Routes.PROCESSES)
                    }
                ) {
                    Text("Start Scaled")
                }
            },
            dismissButton = {
                TextButton(onClick = { showScalingDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(recipe?.name ?: "Recipe Details")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->

        recipe?.let { currentRecipe ->

            LazyColumn(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),

                contentPadding =
                    PaddingValues(16.dp),

                verticalArrangement =
                    Arrangement.spacedBy(16.dp)
            ) {

                item {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {

                        Column(

                            modifier = Modifier
                                .padding(16.dp),

                            verticalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {

                            if (!currentRecipe.imageUri.isNullOrBlank()) {
                                AsyncImage(
                                    model = currentRecipe.imageUri,
                                    contentDescription = "Recipe Image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Text(

                                text = currentRecipe.style,

                                style =
                                    MaterialTheme
                                        .typography
                                        .headlineSmall,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                text = currentRecipe.description,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Text(
                                text = "Batch Size: ${currentRecipe.batchSize} L",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            currentRecipe.abv?.let {

                                Text(
                                    text = "ABV: $it%",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                }

                item {

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Button(
                            onClick = {
                                showScalingDialog = true
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Start Brewing")
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        OutlinedButton(
                            onClick = {
                                navController.navigate("${Routes.ADD_RECIPE}?recipeId=${currentRecipe.id}")
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Edit Recipe")
                        }
                    }
                }

                item {

                    Text(

                        text = "Ingredients",

                        style =
                            MaterialTheme
                                .typography
                                .headlineSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                items(currentRecipe.ingredients) { ingredient ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = ingredient.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "${ingredient.amount} ${ingredient.unit}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                item {

                    Text(

                        text = "Brewing Steps",

                        style =
                            MaterialTheme
                                .typography
                                .headlineSmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }

                items(steps) { step ->

                    Card(

                        modifier = Modifier
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {

                        Column(

                            modifier = Modifier
                                .padding(16.dp),

                            verticalArrangement =
                                Arrangement.spacedBy(4.dp)
                        ) {

                            Text(

                                text = "${step.stepOrder + 1}. ${step.title}",

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            if (step.description.isNotEmpty()) {
                                Text(
                                    text = step.description,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Text(
                                text = "Duration: ${step.durationMinutes} min",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
        } ?: run {
            // Optional: Loading or empty state
        }
    }
}
