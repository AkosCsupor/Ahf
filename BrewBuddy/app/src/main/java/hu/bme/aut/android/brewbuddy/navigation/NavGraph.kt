package hu.bme.aut.android.brewbuddy.navigation
import hu.bme.aut.android.brewbuddy.presentation.process.ProcessScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hu.bme.aut.android.brewbuddy.presentation.home.HomeScreen
import hu.bme.aut.android.brewbuddy.presentation.inventory.InventoryScreen
import hu.bme.aut.android.brewbuddy.presentation.recipe.RecipeScreen
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
    const val ADD_INVENTORY = "add_inventory"
    const val RECIPE_DETAILS = "recipe_details"
    const val BREW_TOOLS = "brew_tools"
    const val BREW_DAY = "brew_day"
    const val BREW_HISTORY = "brew_history"
    const val PROCESSES = "processes"
    const val ACTIVE_PROCESS = "active_process"
    const val INVENTORY_DETAILS = "inventory_details"
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

        composable(
            route = "${Routes.ADD_RECIPE}?recipeId={recipeId}",
            arguments = listOf(
                androidx.navigation.navArgument("recipeId") {
                    type = androidx.navigation.NavType.LongType
                    defaultValue = -1L
                }
            )
        ) {
            AddEditRecipeScreen(
                navController = navController
            )
        }

        composable(Routes.BREW_TOOLS) {
            BrewToolsScreen()
        }

        composable(
            route = "${Routes.RECIPE_DETAILS}/{recipeId}",
            arguments = listOf(
                androidx.navigation.navArgument("recipeId") {
                    type = androidx.navigation.NavType.LongType
                }
            )
        ) {
            RecipeDetailsScreen(
                navController = navController
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
        composable(Routes.PROCESSES) {

            ProcessScreen(
                navController = navController
            )
        }
        composable(
            "${Routes.ACTIVE_PROCESS}/{processId}",
            arguments = listOf(
                androidx.navigation.navArgument("processId") {
                    type = androidx.navigation.NavType.LongType
                }
            )
        ) {

            ActiveProcessScreen(navController = navController)
        }
        composable(
            route = Routes.ADD_INVENTORY
        ) {

            AddEditInventoryScreen(
                navController = navController
            )
        }

        composable(
            route =
                "${Routes.INVENTORY_DETAILS}/{ingredientId}"
        ) {

            AddEditInventoryScreen(
                navController = navController
            )
        }
    }
}
