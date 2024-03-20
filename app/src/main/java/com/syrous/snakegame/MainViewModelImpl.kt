package com.syrous.snakegame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syrous.snakegame.screen.GameScreen
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModelImpl : ViewModel(), GameViewModel, GameController {

    override val currentScreen: MutableStateFlow<GameScreen> =
        MutableStateFlow(GameScreen.START_GAME)

    override var snakeGridState: SnakeGridState = SnakeGridStateImpl()

    private var gameLoop: Job? = null

    override fun startGame() {
        currentScreen.value = GameScreen.GAME_PLAY_SCREEN
        resumeGame()
    }

    override fun pauseGame() {
        TODO("Not yet implemented")
    }

    override fun resumeGame() {
        gameLoop = viewModelScope.launch {
            while (true) {
                delay(160)
                snakeGridState.updateSnakeBodyAfterLoop()
            }
        }
    }

    override fun restartGame() {
        TODO("Not yet implemented")
    }

    override fun moveRight() {
       viewModelScope.launch {
           snakeGridState.moveSnakeRight()
       }
    }

    override fun moveLeft() {
        viewModelScope.launch {
            snakeGridState.moveSnakeLeft()
        }
    }

    override fun moveUp() {
        viewModelScope.launch {
            snakeGridState.moveSnakeUp()
        }
    }

    override fun moveDown() {
        viewModelScope.launch {
            snakeGridState.moveSnakeDown()
        }
    }

    override fun exitGame() {
        currentScreen.value = GameScreen.GAME_OVER_SCREEN
    }
}

enum class Directions(val move: Pair<Int, Int>) {
    LEFT(Pair(-1, 0)),
    RIGHT(Pair(1, 0)),
    UP(Pair(0, -1)),
    DOWN(Pair(0, 1))
}