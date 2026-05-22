package hu.bme.aut.android.brewbuddy.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.bme.aut.android.brewbuddy.navigation.Routes
import hu.bme.aut.android.brewbuddy.presentation.components.DashboardCard
import hu.bme.aut.android.brewbuddy.presentation.home.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(

    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val activeProcesses by viewModel.activeProcesses.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BrewBuddy") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
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

                Column {

                    Text(

                        text = "Welcome back brewer",

                        style =
                            MaterialTheme
                                .typography
                                .headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            item {

                Row(

                    modifier = Modifier
                        .fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(16.dp)
                ) {

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        DashboardCard(

                            title = "Recipes",

                            description =
                                "Manage brew recipes",

                            onClick = {

                                navController.navigate(
                                    Routes.RECIPES
                                )
                            }
                        )

                        Spacer(
                            modifier =
                                Modifier.height(16.dp)
                        )

                        DashboardCard(

                            title = "Brewing",

                            description =
                                "Active brew sessions",

                            onClick = {

                                navController.navigate(
                                    Routes.PROCESSES
                                )
                            }
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        DashboardCard(

                            title = "Inventory",

                            description =
                                "Manage ingredients",

                            onClick = {

                                navController.navigate(
                                    Routes.INVENTORY
                                )
                            }
                        )

                        Spacer(
                            modifier =
                                Modifier.height(16.dp)
                        )

                        DashboardCard(

                            title = "Calculator",

                            description =
                                "ABV and scaling tools",

                            onClick = {

                                navController.navigate(
                                    Routes.BREW_TOOLS
                                )
                            }
                        )
                    }
                }
            }

            item {

                Text(

                    text = "Active Brewing",

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall
                )
            }

            item {

                if (activeProcesses.isEmpty()) {
                    DashboardCard(

                        title = "No Active Processes",

                        description =
                            "Start brewing from a recipe",

                        onClick = {
                            navController.navigate(Routes.RECIPES)
                        }
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        activeProcesses.forEach { process ->
                            DashboardCard(
                                title = process.recipeName,
                                description = "Current Step: ${process.currentStepIndex + 1}",
                                onClick = {
                                    navController.navigate("${Routes.ACTIVE_PROCESS}/${process.id}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}