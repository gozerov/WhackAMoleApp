package ru.gozerov.whackamoleapp.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screens(val simpleRoute: String, val route: String = simpleRoute) {
    object MenuScreen: Screens("menuScreen")
    object GameScreen: Screens(simpleRoute = "gameScreen/", route = "gameScreen/{cells}")
    object MainScreen: Screens("mainScreen")
    object ResultsScreen: Screens(simpleRoute = "resultsScreen/", route = "resultsScreen/{score}")
}

@Composable
fun NavigationRouter(
    startDestination: String,
    tabController: NavController,
    activityController: NavController,
    argument: Pair<String, NavArgumentBuilder.() -> Unit>? = null,
    screens: List<Pair<String, @Composable (
        currentController: NavController,
        tabController: NavController,
        activityController: NavController,
        backStackEntry: NavBackStackEntry?
    ) -> Unit>>
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = startDestination) {
        screens.forEach { pair ->
            if (argument != null) {
                composable(
                    route = pair.first,
                    arguments = listOf(navArgument(argument.first, argument.second))
                ) { backStackEntry ->
                    pair.second(
                        currentController = navController,
                        tabController = tabController,
                        activityController = activityController,
                        backStackEntry = backStackEntry
                    )
                }
            }
            else {
                composable(
                    route = pair.first,
                ) {
                    pair.second(
                        currentController = navController,
                        tabController = tabController,
                        activityController = activityController,
                        backStackEntry = null
                    )
                }
            }
        }
    }
}