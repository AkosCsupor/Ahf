package hu.bme.aut.android.brewbuddy.presentation.activeprocess
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import hu.bme.aut.android.brewbuddy.worker.BrewTimerScheduler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import hu.bme.aut.android.brewbuddy.presentation.activeprocess.viewmodel.ActiveProcessViewModel

@Composable
fun ActiveProcessScreen(

    viewModel: ActiveProcessViewModel =
        hiltViewModel()
) {

    val steps by
        viewModel.steps.collectAsState()

    val currentStepIndex by
        viewModel.currentStepIndex
            .collectAsState()

    val currentStep =
        steps.getOrNull(currentStepIndex)
    val context = LocalContext.current
    Scaffold {

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {

            Text(

                text = "Active Brewing Process",

                style =
                    MaterialTheme
                        .typography
                        .headlineMedium
            )
            LaunchedEffect(currentStep?.id) {

                currentStep?.let {

                    BrewTimerScheduler.scheduleTimer(

                        context = context,

                        stepTitle = it.title,

                        durationMinutes =
                            it.durationMinutes
                    )
                }
            }
            currentStep?.let { step ->

                Card(

                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Column(

                        modifier =
                            Modifier.padding(16.dp),

                        verticalArrangement =
                            Arrangement.spacedBy(
                                12.dp
                            )
                    ) {

                        Text(

                            text =
                                "Step ${
                                    currentStepIndex + 1
                                }",

                            style =
                                MaterialTheme
                                    .typography
                                    .titleLarge
                        )

                        Text(
                            text = step.title,

                            style =
                                MaterialTheme
                                    .typography
                                    .headlineSmall
                        )

                        Text(
                            text =
                                step.description
                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    8.dp
                                )
                        )

                        Text(
                            text =
                                "Duration: ${
                                    step.durationMinutes
                                } min"
                        )
                    }
                }

                Button(

                    onClick = {

                        viewModel.previousStep()
                    },

                    enabled =
                        currentStepIndex > 0
                ) {

                    Text("Previous Step")
                }

                Button(

                    onClick = {

                        viewModel.nextStep()
                    },

                    enabled =
                        currentStepIndex <
                                steps.lastIndex
                ) {

                    Text("Next Step")
                }

                Button(

                    onClick = {

                        viewModel.completeCurrentStep()
                    }
                ) {

                    Text("Complete Step")
                }
            }
        }
    }
}