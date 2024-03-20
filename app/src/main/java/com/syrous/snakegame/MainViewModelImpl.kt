package com.syrous.snakegame

import androidx.lifecycle.ViewModel
import com.syrous.snakegame.screen.GameScreen
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModelImpl : ViewModel(), GameViewModel, GameController {

    override val currentScreen: MutableStateFlow<GameScreen> =
        MutableStateFlow(GameScreen.START_GAME)

    override var snakeGridState: SnakeGridState = SnakeGridState()

    override fun startGame() {
        currentScreen.value = GameScreen.GAME_PLAY_SCREEN
    }

    override fun pauseGame() {
        TODO("Not yet implemented")
    }

    override fun resumeGame() {

    }

    override fun restartGame() {
        TODO("Not yet implemented")
    }

    override fun moveRight() {
        snakeGridState.moveSnakeRight()
    }

    override fun moveLeft() {
        snakeGridState.moveSnakeLeft()
    }

    override fun moveUp() {
        snakeGridState.moveSnakeUp()
    }

    override fun moveDown() {
        snakeGridState.moveSnakeDown()
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