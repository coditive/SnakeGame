package com.syrous.snakegame

interface GameController {

    val snakeGridState: SnakeGridState

    fun startGame()

    fun pauseGame()

    fun resumeGame()

    fun restartGame()

    fun moveRight()

    fun moveLeft()

    fun moveUp()

    fun moveDown()

    fun exitGame()
}