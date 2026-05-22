package hu.bme.aut.android.brewbuddy.presentation.process

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.bme.aut.android.brewbuddy.presentation.components.BrewCard
import hu.bme.aut.android.brewbuddy.presentation.process.viewmodel.ProcessViewModel

@Composable
fun ProcessScreen(

    navController: NavController,

    viewModel: ProcessViewModel =
        hiltViewModel()
) {

    val activeProcesses by
        viewModel.activeProcesses
            .collectAsState()

    val finishedProcesses by
        viewModel.finishedProcesses
            .collectAsState()

    Scaffold {

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(it),

            contentPadding =
                PaddingValues(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            item {

                Text(
                    text = "Active Brewing",

                    style =
                        MaterialTheme
                            .typography
                            .headlineMedium
                )
            }

            items(activeProcesses) { process ->

                BrewCard(

                    title = process.recipeName,

                    subtitle =
                        "Current Step: ${
                            process.currentStepIndex + 1
                        }",

                    modifier =
                        Modifier.clickable {

                            navController.navigate(
                                "active_process/${process.id}"
                            )
                        }
                )
            }

            item {

                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )

                HorizontalDivider()

                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )

                Text(
                    text =
                        "Latest 10 Completed",

                    style =
                        MaterialTheme
                            .typography
                            .headlineMedium
                )
            }

            items(finishedProcesses) { process ->

                BrewCard(

                    title = process.recipeName,

                    subtitle = "Finished"
                )
            }
        }
    }
}