package ru.gozerov.whackamoleapp.screens.menu

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ru.gozerov.whackamoleapp.navigation.NavigationRouter
import ru.gozerov.whackamoleapp.navigation.Screens

@Composable
fun MenuContainer(
    tabController: NavController,
    activityController: NavController
) {
    NavigationRouter(
        startDestination = Screens.MenuScreen.route,
        tabController = tabController,
        activityController = activityController,
        screens = listOf(
            Screens.MenuScreen.route to { _, tabNavController, _ , _->
                MenuScreen(tabNavController)
            }
        )
    )
}