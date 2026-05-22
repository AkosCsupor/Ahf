package hu.bme.aut.android.brewbuddy.presentation.brewday

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.brewbuddy.presentation.brewday.viewmodel.BrewDayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrewDayScreen(
    viewModel: BrewDayViewModel = viewModel()
) {

    val remainingSeconds by viewModel
        .remainingSeconds
        .collectAsState()

    val currentStepIndex by viewModel
        .currentStepIndex
        .collectAsState()

    val isRunning by viewModel
        .isRunning
        .collectAsState()

    val currentStep =
        viewModel.currentStep

    val minutes =
        remainingSeconds / 60

    val seconds =
        remainingSeconds % 60

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Brew Day Mode")
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text =
                    "Step ${currentStepIndex + 1}"
            )

            Card {

                Column(
                    modifier =
                        Modifier.padding(16.dp),

                    verticalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        text = currentStep.title
                    )

                    Text(
                        text =
                            currentStep.description
                    )

                    Text(
                        text =
                            String.format(
                                "%02d:%02d",
                                minutes,
                                seconds
                            )
                    )
                }
            }

            Button(
                onClick = {

                    viewModel.startTimer()
                }
            ) {

                Text(
                    if (isRunning)
                        "Running..."
                    else
                        "Start Timer"
                )
            }

            Button(
                onClick = {

                    viewModel.nextStep()
                }
            ) {

                Text("Next Step")
            }

            Button(
                onClick = {

                    viewModel.resetTimer()
                }
            ) {

                Text("Reset Timer")
            }
        }
    }
}