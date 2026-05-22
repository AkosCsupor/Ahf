package hu.bme.aut.android.brewbuddy.presentation.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import hu.bme.aut.android.brewbuddy.presentation.recipes.viewmodel.AddEditRecipeViewModel

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

                Button(

                    onClick = {

                        viewModel.saveRecipe {

                            navController
                                .popBackStack()
                        }
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Text("Save Recipe")
                }
            }
        }
    }
}