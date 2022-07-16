package ru.gozerov.whackamoleapp.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screens(route: String) {
    object MenuScreen: Screens("menuScreen")
    object GameScreen: Screens("gameScreen")
}

@Composable
fun NavigationRouter(
    startDestination: String,
    parentController: NavController,
    screens: List<Pair<String, @Composable (
        currentController: NavController,
        parentController: NavController
    ) -> Unit>>
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = startDestination) {
        screens.forEach { pair ->
            composable(pair.first) { pair.second(
                currentController = navController,
                parentController = parentController,
            ) }

        }
    }
}
fun NavController.navigate(
    route: String,
    args: Bundle,
    builder: (NavOptionsBuilder.() -> Unit)? = null,
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        val id = destination.id
        builder?.let {
            navigate(id, args, navOptions(it))
        } ?: navigate(id, args, null)
    } else {
        throw IllegalArgumentException("navigation destination $route was not found")
    }
}

fun NavController.getArguments(): Bundle? {
    return this.currentBackStackEntry?.arguments
}
