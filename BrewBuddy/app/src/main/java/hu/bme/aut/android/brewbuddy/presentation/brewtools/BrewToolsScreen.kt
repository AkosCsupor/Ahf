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
        }
    }
}