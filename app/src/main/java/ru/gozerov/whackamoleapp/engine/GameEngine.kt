package ru.gozerov.whackamoleapp.engine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

class GameEngine @Inject constructor(){

    private var cellsNum = 0

    private var currentCell = START

    private var result = 0

    private val _gameState = MutableStateFlow(START)
    val gameState: StateFlow<Int> = _gameState.asStateFlow()

    private val _isGameStarted = MutableStateFlow(false)
    val isGameStarted: StateFlow<Boolean> = _isGameStarted.asStateFlow()

    suspend fun startGame(cells: Int) {
        cellsNum = cells
        generateCell()
    }

    fun onMoleWhacked() {
        result += 1
    }

    fun getMoleWhackedResult(): Int {
        return result
    }

    private var job: Job? = null

    private suspend fun generateCell() {
        coroutineScope {
            delay(500)
            job = launch {
                ensureActive()
                repeat(61) {

                    var num = Random.nextInt(1..cellsNum)
                    if (num==currentCell) {
                        while (true) {
                            num = Random.nextInt(1..cellsNum)
                            if (num!=currentCell) break
                        }
                    }
                    currentCell = num
                    delay(500)
                    if (_gameState.value == START) {
                        _isGameStarted.emit(true)
                        delay(100)
                    }

                    _gameState.emit(currentCell)
                }
                _gameState.emit(END)
            }
        }

    }

    fun clear() {
        job?.cancel()
        _gameState.value = START
        _isGameStarted.value = false
        cellsNum = 0
        currentCell = START
        result = 0
    }

    companion object {
        const val START = -1
        const val END = -2
    }

}