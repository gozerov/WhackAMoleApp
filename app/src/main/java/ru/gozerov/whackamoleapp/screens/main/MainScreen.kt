package ru.gozerov.whackamoleapp.screens.main

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.gozerov.whackamoleapp.R
import ru.gozerov.whackamoleapp.navigation.Screens
import ru.gozerov.whackamoleapp.screens.game.GameContainer
import ru.gozerov.whackamoleapp.screens.menu.MenuContainer
import ru.gozerov.whackamoleapp.utils.Constants.ARG_CELLS
import ru.gozerov.whackamoleapp.utils.Constants.CELLS_DEFAULT
import ru.gozerov.whackamoleapp.utils.Constants.KEY_CELLS
import ru.gozerov.whackamoleapp.utils.sharedPrefs

@Composable
fun MainScreen(
    activityNavController: NavController,
    tabNavController: NavHostController
) {
    val context = LocalContext.current
    val isSelectedMenu = remember { mutableStateOf(true) }
    Scaffold (
        bottomBar = {
            BottomAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.background
            ) {
                BottomNavigationItem(
                    selected = isSelectedMenu.value,
                    onClick = {
                        tabNavController.navigate(Screens.MenuScreen.route) {
                            popUpTo(
                                route = tabNavController.currentBackStackEntry?.destination?.route ?: return@navigate
                            ) { 
                                inclusive = true 
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.menu),
                            fontSize = 14.sp
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = stringResource(id = R.string.menu)
                        )
                    }
                )
                BottomNavigationItem(
                    selected = !isSelectedMenu.value,
                    onClick = {
                        val cells = context.sharedPrefs.getInt(KEY_CELLS, CELLS_DEFAULT)
                        tabNavController.navigate(Screens.GameScreen.simpleRoute + cells)  {
                            launchSingleTop = true
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.game),
                            fontSize = 14.sp
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = stringResource(id = R.string.game)
                        )
                    }
                )
            }
        }
    ) {
        NavHost(
            navController = tabNavController,
            startDestination = Screens.MenuScreen.route
        ) {
            composable(Screens.MenuScreen.route) {
                isSelectedMenu.value = true
                MenuContainer(
                    tabController = tabNavController,
                    activityController = activityNavController
                )
            }
            composable(
                route = Screens.GameScreen.route,
                arguments = listOf(navArgument(ARG_CELLS) { type = NavType.IntType })
            ) { backStackEntry ->
                isSelectedMenu.value = false
                GameContainer(
                    tabController = tabNavController,
                    activityController = activityNavController,
                    cells = backStackEntry.arguments?.getInt(ARG_CELLS) ?: CELLS_DEFAULT
                )
            }
        }
    }
}