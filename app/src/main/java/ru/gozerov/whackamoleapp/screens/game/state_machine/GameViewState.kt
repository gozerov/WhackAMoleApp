package ru.gozerov.whackamoleapp.screens.game.state_machine

sealed class GameViewState {
    object StartGame: GameViewState()
    object GameStarted: GameViewState()

    data class OnNewMole(
        val cellNum: Int
    ): GameViewState()
    data class EndGame(
        val result: Int
    ): GameViewState()
}