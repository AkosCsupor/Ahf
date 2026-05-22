package hu.bme.aut.android.brewbuddy.presentation.inventory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.bme.aut.android.brewbuddy.presentation.components.InventoryOverviewCard
import hu.bme.aut.android.brewbuddy.presentation.inventory.viewmodel.InventoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(

    navController: NavController,

    viewModel: InventoryViewModel =
        hiltViewModel()
) {

    val ingredients by
    viewModel.ingredients
        .collectAsState()

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text("Inventory")
                }
            )
        },

        floatingActionButton = {

            FloatingActionButton(

                onClick = {

                    navController.navigate(
                        "add_inventory"
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

                InventoryOverviewCard(

                    totalItems =
                        ingredients.size
                )
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

            items(ingredients) { ingredient ->

                Card(

                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                            navController.navigate(

                                "inventory_details/${ingredient.id}"
                            )
                        }
                ) {

                    androidx.compose.foundation.layout.Column(

                        modifier = Modifier
                            .padding(16.dp)
                    ) {

                        Text(

                            text =
                                ingredient.name,

                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium
                        )

                        Text(

                            text =
                                "${ingredient.amount} ${ingredient.unit}"
                        )
                    }
                }
            }
        }
    }
}