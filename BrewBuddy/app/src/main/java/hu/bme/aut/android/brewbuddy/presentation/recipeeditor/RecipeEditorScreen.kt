package hu.bme.aut.android.brewbuddy.presentation.recipeeditor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecipeEditorScreen() {

    var stepTitle by remember {

        mutableStateOf("")
    }

    var stepDescription by remember {

        mutableStateOf("")
    }

    var duration by remember {

        mutableStateOf("")
    }

    Scaffold(

        floatingActionButton = {

            FloatingActionButton(
                onClick = {

                }
            ) {

                Text("+")
            }
        }
    ) {

        Column(

            modifier = Modifier
                .padding(it)
                .padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(

                value = stepTitle,

                onValueChange = {

                    stepTitle = it
                },

                label = {

                    Text("Step Title")
                }
            )

            OutlinedTextField(

                value = stepDescription,

                onValueChange = {

                    stepDescription = it
                },

                label = {

                    Text("Description")
                }
            )

            OutlinedTextField(

                value = duration,

                onValueChange = {

                    duration = it
                },

                label = {

                    Text("Duration Minutes")
                }
            )
        }
    }
}