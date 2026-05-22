package hu.bme.aut.android.brewbuddy.presentation.activeprocess

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import hu.bme.aut.android.brewbuddy.navigation.Routes
import hu.bme.aut.android.brewbuddy.presentation.activeprocess.viewmodel.ActiveProcessViewModel

@Composable
fun ActiveProcessScreen(
    navController: NavController,
    viewModel: ActiveProcessViewModel = hiltViewModel()
) {
    val steps by viewModel.steps.collectAsState()
    val currentStepIndex by viewModel.currentStepIndex.collectAsState()
    val selectedStepIndex by viewModel.selectedStepIndex.collectAsState()
    val remainingSeconds by viewModel.remainingSeconds.collectAsState()
    val isPaused by viewModel.isPaused.collectAsState()

    val currentStep = steps.getOrNull(selectedStepIndex)
    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60
    val isViewingCurrent = viewModel.isViewingCurrentStep()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Active Brewing Process",
                style = MaterialTheme.typography.headlineMedium
            )

            currentStep?.let { step ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Step ${selectedStepIndex + 1}",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = step.title,
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Text(text = step.description)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Duration: ${step.durationMinutes} min")

                        if (isViewingCurrent) {
                            Text(
                                text = "Remaining: %02d:%02d".format(minutes, seconds),
                                style = MaterialTheme.typography.titleLarge,
                                color = if (remainingSeconds == 0L) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { viewModel.togglePause() },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(if (isPaused) "Resume" else "Pause")
                                }
                                Button(
                                    onClick = { viewModel.resetTimer() },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Reset")
                                }
                            }
                        }
                    }
                }

                Button(
                    onClick = { viewModel.previousStep() },
                    enabled = selectedStepIndex > 0,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Previous Step")
                }

                Button(
                    onClick = { viewModel.nextStep() },
                    enabled = selectedStepIndex < steps.lastIndex,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Next Step")
                }

                if (!isViewingCurrent) {
                    Button(
                        onClick = { viewModel.jumpToCurrentStep() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Back To Current Step")
                    }
                }

                Button(
                    onClick = {
                        viewModel.completeCurrentStep {
                            navController.navigate(Routes.PROCESSES) {
                                popUpTo(Routes.HOME)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isViewingCurrent
                ) {
                    Text("Complete Step")
                }
            }
        }
    }
}
