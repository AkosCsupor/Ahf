package hu.bme.aut.android.brewbuddy.presentation.recipedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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

    recipe ?: return

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(recipe!!.name)
                }
            )
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

                Card {

                    Column(

                        modifier = Modifier
                            .padding(16.dp),

                        verticalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {

                        Text(

                            text =
                                recipe!!.style,

                            style =
                                MaterialTheme
                                    .typography
                                    .headlineSmall
                        )

                        Text(
                            text =
                                recipe!!.description
                        )

                        Text(
                            text =
                                "Batch Size: ${recipe!!.batchSize} L"
                        )

                        recipe!!.abv?.let {

                            Text(
                                text =
                                    "ABV: $it%"
                            )
                        }
                    }
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

            items(steps) { step ->

                Card(

                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Column(

                        modifier = Modifier
                            .padding(16.dp),

                        verticalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {

                        Text(

                            text = step.title,

                            style =
                                MaterialTheme
                                    .typography
                                    .titleLarge
                        )

                        Text(
                            text =
                                step.description
                        )

                        Text(
                            text =
                                "Duration: ${step.durationMinutes} min"
                        )
                    }
                }
            }

            item {

                Button(

                    onClick = {

                        viewModel.startBrewing()

                        navController.navigate(
                            "brewing"
                        )
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Text("Start Brewing")
                }
            }
        }
    }
}