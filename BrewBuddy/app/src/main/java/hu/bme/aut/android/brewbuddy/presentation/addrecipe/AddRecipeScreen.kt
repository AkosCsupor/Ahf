package hu.bme.aut.android.brewbuddy.presentation.addrecipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hu.bme.aut.android.brewbuddy.domain.model.Recipe
import hu.bme.aut.android.brewbuddy.presentation.recipe.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    navController: NavController,
    viewModel: RecipeViewModel
) {

    var name by remember {
        mutableStateOf("")
    }

    var style by remember {
        mutableStateOf("")
    }

    var batchSize by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add Recipe")
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = {
                    Text("Recipe Name")
                }
            )

            OutlinedTextField(
                value = style,
                onValueChange = {
                    style = it
                },
                label = {
                    Text("Beer Style")
                }
            )

            OutlinedTextField(
                value = batchSize,
                onValueChange = {
                    batchSize = it
                },
                label = {
                    Text("Batch Size (L)")
                }
            )

            Button(
                onClick = {

                    val recipe = Recipe(
                        id = 0,
                        name = name,
                        style = style,
                        batchSize = batchSize.toDoubleOrNull() ?: 0.0,
                        description = description
                    )

                    viewModel.insertRecipe(recipe)

                    navController.popBackStack()
                }
            ) {

                Text("Save Recipe")
            }
        }
    }
}