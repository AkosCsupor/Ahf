package hu.bme.aut.android.brewbuddy.presentation.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.bme.aut.android.brewbuddy.navigation.Routes
import hu.bme.aut.android.brewbuddy.presentation.components.RecipeCard
import hu.bme.aut.android.brewbuddy.presentation.recipe.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(

    navController: NavController,

    viewModel: RecipeViewModel =
        hiltViewModel()
) {

    val recipes by
    viewModel.recipes
        .collectAsState()

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text("Recipes")
                }
            )
        },

        floatingActionButton = {

            FloatingActionButton(

                onClick = {

                    navController.navigate(
                        Routes.ADD_RECIPE
                    )
                }
            ) {

                Text("+")
            }
        }
    ) { padding ->

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

                Text(

                    text = "Recipe Collection",

                    style =
                        MaterialTheme
                            .typography
                            .headlineMedium
                )
            }

            items(recipes) { recipe ->

                RecipeCard(

                    recipe = recipe,

                    onClick = {

                        navController.navigate(

                            "${Routes.RECIPE_DETAILS}/${recipe.id}"
                        )
                    }
                )
            }
        }
    }
}
