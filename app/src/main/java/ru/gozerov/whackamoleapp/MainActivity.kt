package ru.gozerov.whackamoleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ru.gozerov.whackamoleapp.navigation.Screens
import ru.gozerov.whackamoleapp.screens.main.MainScreen
import ru.gozerov.whackamoleapp.screens.results.ResultsScreen
import ru.gozerov.whackamoleapp.ui.theme.WhackAMoleAppTheme
import ru.gozerov.whackamoleapp.utils.Constants.ARG_SCORE
import ru.gozerov.whackamoleapp.utils.Constants.HIGHEST_SCORE_DEFAULT

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activityNavController = rememberNavController()
            val tabNavController = rememberNavController()
            WhackAMoleAppTheme(
                darkTheme = false
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        navController = activityNavController,
                        startDestination = Screens.MainScreen.route
                    ) {
                        composable(Screens.MainScreen.route) {
                            MainScreen(
                                activityNavController = activityNavController,
                                tabNavController = tabNavController
                            )
                        }

                        composable(
                            route = Screens.ResultsScreen.route,
                            arguments = listOf(
                                navArgument(ARG_SCORE) { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            ResultsScreen(
                                score = backStackEntry.arguments?.getInt(ARG_SCORE) ?: HIGHEST_SCORE_DEFAULT,
                                activityController = activityNavController,
                                tabController = tabNavController
                            )
                        }
                    }
                }
            }
        }
    }
}