package hu.bme.aut.android.brewbuddy.presentation.brewtools

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrewToolsScreen() {

    var og by remember {
        mutableStateOf("")
    }

    var fg by remember {
        mutableStateOf("")
    }

    var abv by remember {
        mutableStateOf(0.0)
    }

    var originalVolume by remember {
        mutableStateOf("")
    }

    var targetVolume by remember {
        mutableStateOf("")
    }

    var originalIngredient by remember {
        mutableStateOf("")
    }

    var scaledIngredient by remember {
        mutableStateOf(0.0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Brew Tools")
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

            Text("Alcohol Calculator")

            OutlinedTextField(
                value = og,
                onValueChange = {
                    og = it
                },
                label = {
                    Text("Original Gravity")
                }
            )

            OutlinedTextField(
                value = fg,
                onValueChange = {
                    fg = it
                },
                label = {
                    Text("Final Gravity")
                }
            )

            Button(
                onClick = {

                    val ogValue =
                        og.toDoubleOrNull() ?: 0.0

                    val fgValue =
                        fg.toDoubleOrNull() ?: 0.0

                    abv =
                        (ogValue - fgValue) * 131.25
                }
            ) {

                Text("Calculate ABV")
            }

            Card {

                Column(
                    modifier =
                        Modifier.padding(16.dp)
                ) {

                    Text(
                        text =
                            "Estimated ABV: " +
                                    "%.2f".format(abv) +
                                    "%"
                    )
                }
            }

            Text("Recipe Scaling")

            OutlinedTextField(
                value = originalVolume,
                onValueChange = {
                    originalVolume = it
                },
                label = {
                    Text("Original Volume")
                }
            )

            OutlinedTextField(
                value = targetVolume,
                onValueChange = {
                    targetVolume = it
                },
                label = {
                    Text("Target Volume")
                }
            )

            OutlinedTextField(
                value = originalIngredient,
                onValueChange = {
                    originalIngredient = it
                },
                label = {
                    Text("Ingredient Amount")
                }
            )

            Button(
                onClick = {

                    val originalVol =
                        originalVolume.toDoubleOrNull()
                            ?: 1.0

                    val targetVol =
                        targetVolume.toDoubleOrNull()
                            ?: 1.0

                    val ingredient =
                        originalIngredient.toDoubleOrNull()
                            ?: 0.0

                    scaledIngredient =
                        ingredient *
                                (targetVol / originalVol)
                }
            ) {

                Text("Scale Recipe")
            }

            Card {

                Column(
                    modifier =
                        Modifier.padding(16.dp)
                ) {

                    Text(
                        text =
                            "Scaled Ingredient: " +
                                    "%.2f".format(
                                        scaledIngredient
                                    )
                    )
                }
            }
        }
    }
}