package com.syrous.snakegame

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface SnakeGridState {

    val snakeEvent: SharedFlow<SnakeEvent>
    val snakeGrid: StateFlow<List<Pair<Int, Int>>>
    val foodGrid: StateFlow<Set<Pair<Int, Int>>>
    val score: StateFlow<Int>
    fun updateGridSize(width: Int, height: Int)
    fun restartGame()
    suspend fun updateSnakeBodyAfterLoop()
    suspend fun moveSnakeLeft()
    suspend fun moveSnakeRight()
    suspend fun moveSnakeUp()
    suspend fun moveSnakeDown()
}