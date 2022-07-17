package ru.gozerov.whackamoleapp.screens.game

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import ru.gozerov.whackamoleapp.navigation.NavigationRouter
import ru.gozerov.whackamoleapp.navigation.Screens
import ru.gozerov.whackamoleapp.utils.Constants.ARG_SCORE

@Composable
fun GameContainer(
    tabController: NavController,
    activityController: NavController,
    cells: Int
) {
    NavigationRouter(
        startDestination = Screens.GameScreen.route,
        tabController = tabController,
        activityController = activityController,
        argument = ARG_SCORE to { type = NavType.IntType },
        screens = listOf(
            Screens.GameScreen.route to { _, tabNavController, activityNavController, _->
                GameScreen(tabNavController, activityNavController, cells)
            },

        )
    )
}