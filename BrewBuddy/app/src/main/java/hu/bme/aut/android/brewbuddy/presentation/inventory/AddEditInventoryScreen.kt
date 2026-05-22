package hu.bme.aut.android.brewbuddy.presentation.inventory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.bme.aut.android.brewbuddy.presentation.inventory.viewmodel.AddEditInventoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditInventoryScreen(

    navController: NavController,

    viewModel:
        AddEditInventoryViewModel =
            hiltViewModel()
) {

    val name by
        viewModel.name.collectAsState()

    val amount by
        viewModel.amount.collectAsState()

    val unit by
        viewModel.unit.collectAsState()

    val notes by
        viewModel.notes.collectAsState()

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text("Ingredient")
                }
            )
        }
    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(
                    rememberScrollState()
                ),

            verticalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(

                value = name,

                onValueChange = {

                    viewModel.updateName(it)
                },

                label = {

                    Text("Name")
                },

                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(

                value = amount,

                onValueChange = {

                    viewModel.updateAmount(it)
                },

                label = {

                    Text("Amount")
                },

                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(

                value = unit,

                onValueChange = {

                    viewModel.updateUnit(it)
                },

                label = {

                    Text("Unit")
                },

                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(

                value = notes,

                onValueChange = {

                    viewModel.updateNotes(it)
                },

                label = {

                    Text("Notes")
                },

                modifier = Modifier
                    .fillMaxWidth()
            )

            Button(

                onClick = {

                    viewModel.saveIngredient {

                        navController.popBackStack()
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Text("Save")
            }

            Button(

                onClick = {

                    viewModel.deleteIngredient {

                        navController.popBackStack()
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Text("Delete")
            }
        }
    }
}