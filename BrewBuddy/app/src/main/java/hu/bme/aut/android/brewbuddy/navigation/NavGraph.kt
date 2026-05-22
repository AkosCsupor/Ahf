package hu.bme.aut.android.brewbuddy.navigation
import hu.bme.aut.android.brewbuddy.presentation.process.ProcessScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hu.bme.aut.android.brewbuddy.presentation.home.HomeScreen
import hu.bme.aut.android.brewbuddy.presentation.inventory.InventoryScreen
import hu.bme.aut.android.brewbuddy.presentation.recipe.RecipeScreen
import hu.bme.aut.android.brewbuddy.presentation.addrecipe.AddRecipeScreen
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.brewbuddy.presentation.brewday.BrewDayScreen
import hu.bme.aut.android.brewbuddy.presentation.brewhistory.BrewHistoryScreen
import hu.bme.aut.android.brewbuddy.presentation.recipedetails.RecipeDetailsScreen
import hu.bme.aut.android.brewbuddy.presentation.brewtools.BrewToolsScreen
import hu.bme.aut.android.brewbuddy.presentation.activeprocess.ActiveProcessScreen
import hu.bme.aut.android.brewbuddy.presentation.inventory.AddEditInventoryScreen
import hu.bme.aut.android.brewbuddy.presentation.recipe.AddEditRecipeScreen

object Routes {
    const val HOME = "home"
    const val RECIPES = "recipes"
    const val INVENTORY = "inventory"
    const val ADD_RECIPE = "add_recipe"
    const val RECIPE_DETAILS = "recipe_details"
    const val BREW_TOOLS = "brew_tools"
    const val BREW_DAY = "brew_day"
    const val BREW_HISTORY = "brew_history"
}

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(navController)
        }

        composable(Routes.RECIPES) {
            RecipeScreen(
                navController = navController
            )
        }

        composable(Routes.INVENTORY) {
            InventoryScreen(
                navController = navController
            )
        }

        composable(Routes.ADD_RECIPE) {
            AddRecipeScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }

        composable(Routes.BREW_TOOLS) {
            BrewToolsScreen()
        }

        composable(
            route = "${Routes.RECIPE_DETAILS}/{recipeId}"
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments
                ?.getString("recipeId")
                ?.toLongOrNull()
                ?: 0L

            RecipeDetailsScreen(
                navController = navController,
                recipeId = recipeId
            )
        }

        composable(Routes.BREW_DAY) {
            BrewDayScreen()
        }

        composable(Routes.BREW_HISTORY) {
            BrewHistoryScreen(
                navController = navController
            )
        }
        composable("processes") {

            ProcessScreen(
                navController = navController
            )
        }
        composable(
            "active_process/{processId}"
        ) {

            ActiveProcessScreen()
        }
        composable(
            "ingredient_editor/{ingredientId}"
        ) {

        }
        composable(
            route = "recipe_details/{recipeId}"
        ){

        }
        composable(
            route = "add_inventory"
        ) {

            AddEditInventoryScreen(
                navController = navController
            )
        }

        composable(
            route =
                "inventory_details/{ingredientId}"
        ) {

            AddEditInventoryScreen(
                navController = navController
            )
        }
            composable(
                route = "add_recipe"
            ) {

            }
            composable(
                route = "add_recipe"
            ) {

                AddEditRecipeScreen(
                    navController = navController
                )
            }




        }
    }

