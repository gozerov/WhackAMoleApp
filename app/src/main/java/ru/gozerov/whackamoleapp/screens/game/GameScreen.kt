package ru.gozerov.whackamoleapp.screens.game

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.gozerov.whackamoleapp.screens.game.state_machine.GameIntent.EnterScreen
import ru.gozerov.whackamoleapp.screens.game.state_machine.GameIntent.ExitScreen
import ru.gozerov.whackamoleapp.screens.game.state_machine.GameViewState.*
import ru.gozerov.whackamoleapp.screens.game.views.*

@Composable
fun GameScreen(
    tabNavController: NavController,
    activityController: NavController,
    cells: Int
) {
    val states = (1..cells).map {
        remember {
            mutableStateOf(false)
        }
    }

    val viewModel = hiltViewModel<GameViewModel>()
    val viewState = viewModel.gameViewState.collectAsState(StartGame)

    val isStarted = remember { mutableStateOf(false) }
    val score = remember { mutableStateOf(0) }

    LaunchedEffect(key1 = Unit) {
        viewModel.handleIntent(EnterScreen(cells))
    }
    DisposableEffect(key1 = viewModel.gameViewState) {
        onDispose {
            isStarted.value = false
            states.forEach { if (it.value) it.value = false }
            viewModel.handleIntent(ExitScreen)
        }
    }

    GameScore(isStarted, score)
    GameTimer(isStarted)
    GameGrid(states = states, viewModel = viewModel, score)

    when (val state = viewState.value) {
        is StartGame -> {
            LoadingGame()
        }
        is GameStarted -> {
            isStarted.value = true
        }
        is OnNewMole -> {
            val cell = state.cellNum
            states.forEach { if (it.value) it.value = false }
            states[cell-1].value = true
        }
        is EndGame -> {
            isStarted.value = false
            states.forEach { if (it.value) it.value = false }
            EndGame(
                activityController = activityController,
                tabNavController = tabNavController,
                state = state
            )
        }
    }
}
