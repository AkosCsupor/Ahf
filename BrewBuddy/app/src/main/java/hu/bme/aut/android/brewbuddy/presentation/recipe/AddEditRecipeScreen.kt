package hu.bme.aut.android.brewbuddy.presentation.recipe

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import hu.bme.aut.android.brewbuddy.presentation.recipe.viewmodel.AddEditRecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditRecipeScreen(

    navController: NavController,

    viewModel:
        AddEditRecipeViewModel =
            hiltViewModel()
) {

    val name by
        viewModel.name.collectAsState()

    val style by
        viewModel.style.collectAsState()

    val description by
        viewModel.description.collectAsState()

    val batchSize by
        viewModel.batchSize.collectAsState()

    val abv by
        viewModel.abv.collectAsState()

    val imageUri by
        viewModel.imageUri.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            viewModel.updateImageUri(uri?.toString())
        }
    )

    val ingredients by
        viewModel.ingredients.collectAsState()

    val steps by
        viewModel.steps.collectAsState()

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text("Recipe Editor")
                }
            )
        }
    ) { padding ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {

            item {

                OutlinedTextField(

                    value = name,

                    onValueChange = {

                        viewModel.updateName(it)
                    },

                    label = {

                        Text("Recipe Name")
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            item {

                OutlinedTextField(

                    value = style,

                    onValueChange = {

                        viewModel.updateStyle(it)
                    },

                    label = {

                        Text("Style")
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            item {

                OutlinedTextField(

                    value = description,

                    onValueChange = {

                        viewModel
                            .updateDescription(it)
                    },

                    label = {

                        Text("Description")
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            item {

                OutlinedTextField(

                    value = batchSize,

                    onValueChange = {

                        viewModel
                            .updateBatchSize(it)
                    },

                    label = {

                        Text("Batch Size")
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            item {

                OutlinedTextField(

                    value = abv,

                    onValueChange = {

                        viewModel.updateAbv(it)
                    },

                    label = {

                        Text("ABV")
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            item {
                Text(
                    text = "Recipe Image",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable {
                            launcher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    if (imageUri != null) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "Selected Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.AddAPhoto,
                                    contentDescription = "Add Photo",
                                    modifier = Modifier.size(48.dp)
                                )
                                Text("Select Recipe Image")
                            }
                        }
                    }
                }
            }

            item {

                Text(

                    text = "Ingredients",

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall
                )
            }

            itemsIndexed(ingredients) { index, ingredient ->
                Card {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Ingredient ${index + 1}",
                                style = MaterialTheme.typography.titleMedium
                            )

                            IconButton(
                                onClick = {
                                    viewModel.deleteIngredientAt(index)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Ingredient",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }

                        OutlinedTextField(
                            value = ingredient.name,
                            onValueChange = {
                                viewModel.updateIngredientName(index, it)
                            },
                            label = { Text("Name") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = if (ingredient.amount == 0.0) "" else ingredient.amount.toString(),
                                onValueChange = {
                                    viewModel.updateIngredientAmount(index, it)
                                },
                                label = { Text("Amount") },
                                modifier = Modifier.weight(1f)
                            )

                            OutlinedTextField(
                                value = ingredient.unit,
                                onValueChange = {
                                    viewModel.updateIngredientUnit(index, it)
                                },
                                label = { Text("Unit") },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        viewModel.addIngredient()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Ingredient")
                }
            }

            item {

                Text(

                    text = "Brewing Steps",

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall
                )
            }

            itemsIndexed(steps) {

                    index,
                    step ->

                Card {

                    Column(

                        modifier = Modifier
                            .padding(16.dp),

                        verticalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Step ${index + 1}",
                                style = MaterialTheme.typography.titleMedium
                            )

                            IconButton(
                                onClick = {
                                    viewModel.deleteStepAt(index)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Step",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }

                        OutlinedTextField(

                            value = step.title,

                            onValueChange = {

                                viewModel
                                    .updateStepTitle(
                                        index,
                                        it
                                    )
                            },

                            label = {

                                Text("Step Title")
                            },

                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        OutlinedTextField(

                            value =
                                step.description,

                            onValueChange = {

                                viewModel
                                    .updateStepDescription(
                                        index,
                                        it
                                    )
                            },

                            label = {

                                Text("Description")
                            },

                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        OutlinedTextField(

                            value =
                                step.durationMinutes
                                    .toString(),

                            onValueChange = {

                                viewModel
                                    .updateStepDuration(
                                        index,
                                        it
                                    )
                            },

                            label = {

                                Text("Minutes")
                            },

                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }

            item {

                Button(

                    onClick = {

                        viewModel.addStep()
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Text("Add Step")
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = {
                            viewModel.saveRecipe {
                                navController.popBackStack()
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Save Recipe")
                    }

                    // Csak akkor mutatjuk a törlés gombot, ha már létező receptet szerkesztünk
                    if (viewModel.recipeId != null) {
                        Button(
                            onClick = {
                                viewModel.deleteRecipe {
                                    navController.popBackStack(
                                        hu.bme.aut.android.brewbuddy.navigation.Routes.RECIPES,
                                        false
                                    )
                                }
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Delete Recipe")
                        }
                    }
                }
            }
        }
    }
}
