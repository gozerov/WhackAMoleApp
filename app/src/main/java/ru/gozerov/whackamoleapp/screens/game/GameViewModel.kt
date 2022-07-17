package ru.gozerov.whackamoleapp.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.whackamoleapp.engine.GameEngine
import ru.gozerov.whackamoleapp.screens.game.state_machine.GameIntent
import ru.gozerov.whackamoleapp.screens.game.state_machine.GameIntent.*
import ru.gozerov.whackamoleapp.screens.game.state_machine.GameViewState
import ru.gozerov.whackamoleapp.screens.game.state_machine.GameViewState.*
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameEngine: GameEngine
): ViewModel() {

    private val _gameViewState: MutableStateFlow<GameViewState> = MutableStateFlow(StartGame)
    val gameViewState: StateFlow<GameViewState> = _gameViewState.asStateFlow()

    init {
        collectState()
    }

    private fun collectState() {
        viewModelScope.launch {
            launch {
                gameEngine.gameState.collect { cell ->
                    when (cell) {
                        GameEngine.START -> {}
                        GameEngine.END -> {
                            _gameViewState.emit(EndGame(gameEngine.getMoleWhackedResult()))
                        }
                        else -> {
                            _gameViewState.emit(OnNewMole(cell))
                        }
                    }
                }
            }
            launch {
                gameEngine.isGameStarted.collect { isStarted ->
                    if (isStarted) {
                        _gameViewState.emit(GameStarted)
                    }
                }
            }

        }
    }

    fun handleIntent(intent: GameIntent) {
        when (intent) {
            is EnterScreen -> {
                viewModelScope.launch {
                    _gameViewState.emit(StartGame)
                    gameEngine.startGame(intent.cells)
                }

            }
            is OnMoleWhacked -> {
                gameEngine.onMoleWhacked()
            }
            is ExitScreen -> {
                gameEngine.clear()
            }
        }
    }

}