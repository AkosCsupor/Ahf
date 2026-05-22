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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hu.bme.aut.android.brewbuddy.presentation.components.DashboardCard

@Composable
fun HomeScreen(

    navController: NavController
) {

    LazyColumn(

        modifier = Modifier
            .fillMaxSize(),

        contentPadding =
            PaddingValues(16.dp),

        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {

        item {

            Column {

                Text(

                    text = "BrewBuddy",

                    style =
                        MaterialTheme
                            .typography
                            .headlineLarge
                )

                Text(

                    text =
                        "Welcome back brewer",

                    style =
                        MaterialTheme
                            .typography
                            .bodyLarge
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
                                "recipes"
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
                                "brewing"
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
                                "inventory"
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
                                "calculator"
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

            DashboardCard(

                title = "No Active Processes",

                description =
                    "Start brewing from a recipe",

                onClick = {

                }
            )
        }
    }
}