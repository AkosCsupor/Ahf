package hu.bme.aut.android.brewbuddy.presentation.brewhistory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import hu.bme.aut.android.brewbuddy.presentation.brewhistory.viewmodel.BrewHistoryViewModel
import hu.bme.aut.android.brewbuddy.presentation.components.BrewCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrewHistoryScreen(
    navController: NavController,
    viewModel: BrewHistoryViewModel = hiltViewModel()
) {
    val brewHistory by viewModel.brewHistory.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Brew History")
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
            Button(
                onClick = {
                    viewModel.addSampleHistory()
                }
            ) {
                Text("Add Sample Brew")
            }

            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(brewHistory) { brew ->
                    BrewCard(
                        title = brew.recipeName,
                        subtitle = "${brew.brewDate} • ${brew.abv}% ABV",
                        modifier = Modifier.clickable {
                            // Navigation could be added here if BrewHistory had a recipeId
                        }
                    )
                }
            }
        }
    }
}
