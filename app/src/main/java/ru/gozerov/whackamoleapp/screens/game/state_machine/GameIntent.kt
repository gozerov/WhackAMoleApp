package ru.gozerov.whackamoleapp.screens.game.state_machine

sealed class GameIntent {
    data class EnterScreen(
        val cells: Int
    ): GameIntent()

    object OnMoleWhacked: GameIntent()

    object ExitScreen: GameIntent()
}