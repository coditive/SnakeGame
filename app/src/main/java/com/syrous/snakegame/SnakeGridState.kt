package com.syrous.snakegame

import android.util.Log
import com.syrous.snakegame.util.plus
import kotlinx.coroutines.flow.MutableStateFlow

class SnakeGridState {

    private var gridLength = 0
    private var gridWidth = 0
    private var snakeLength = 10
    private var restrictedDirection: Directions = getOppositeDirection(Directions.RIGHT)
    var snakeGrid = MutableStateFlow<List<Pair<Int, Int>>>(listOf())
    val foodGrid = MutableStateFlow<List<Pair<Int, Int>>>(listOf())

    fun updateGridSize(width: Int, length: Int) {
        if (gridWidth != width && gridLength != length) {
            gridWidth = width
            gridLength = length
            Log.d("SnakeGridState", "updateGridSize -> length = $gridLength, width = $gridWidth")
            drawSnake()
        }
    }

    private fun drawSnake() {
        snakeGrid.value = buildList {
            repeat(snakeLength) {
                add(gridWidth / 2 + it to gridLength / 2)
            }
        }
    }

    fun moveSnakeLeft() {
        val currList = snakeGrid.value.toMutableList()
        Log.d("SnakeGridState", "move Left -> currList -> $currList")
        val head = snakeGrid.value.first()
        val tail = snakeGrid.value.last()
        if (restrictedDirection != Directions.LEFT) {
            val newHead = head + Directions.LEFT.move
            currList.add(0, newHead)
            currList.remove(tail)
            restrictedDirection = getOppositeDirection(Directions.LEFT)
            Log.d(
                "SnakeGridState",
                "move Left -> snakeGrid -> ${snakeGrid.value}, newHead -> $newHead, restrictedDirection -> $restrictedDirection"
            )
            snakeGrid.value = currList
        }
    }

    fun moveSnakeRight() {
        val currList = snakeGrid.value.toMutableList()
        Log.d("SnakeGridState", "move Right -> currList -> $currList")
        val head = snakeGrid.value.first()
        val tail = snakeGrid.value.last()
        if (restrictedDirection != Directions.RIGHT) {
            val newTail = tail + Directions.RIGHT.move
            currList.add(newTail)
            currList.remove(head)
            restrictedDirection = getOppositeDirection(Directions.RIGHT)
            Log.d(
                "SnakeGridState",
                "move Right -> currList -> ${snakeGrid.value}, newTail -> $newTail, restrictedDirection -> $restrictedDirection"
            )
            snakeGrid.value = currList
        }
    }

    fun moveSnakeUp() {
        val currList = snakeGrid.value.toMutableList()
        Log.d("SnakeGridState", "move Up -> currList -> $currList")
        val head = snakeGrid.value.first()
        val tail = snakeGrid.value.last()
        if (restrictedDirection != Directions.UP) {
            val newHead = head + Directions.UP.move
            currList.add(0, newHead)
            currList.remove(tail)
            restrictedDirection = getOppositeDirection(Directions.UP)
            Log.d(
                "SnakeGridState",
                "move Up -> snakeGrid -> ${snakeGrid.value}, newHead -> $newHead, restrictedDirection -> $restrictedDirection"
            )
            snakeGrid.value = currList
        }
    }

    fun moveSnakeDown() {
        val currList = snakeGrid.value.toMutableList()
        Log.d("SnakeGridState", "move Down -> currList -> $currList")
        val head = snakeGrid.value.first()
        val tail = snakeGrid.value.last()
        if (restrictedDirection != Directions.DOWN) {
            val newTail = tail + Directions.DOWN.move
            currList.add(newTail)
            currList.remove(head)
            restrictedDirection = getOppositeDirection(Directions.DOWN)
            Log.d(
                "SnakeGridState",
                "move Down -> currList -> ${snakeGrid.value}, newTail -> $newTail, restrictedDirection -> $restrictedDirection"
            )
            snakeGrid.value = currList
        }
    }

    private fun getOppositeDirection(direction: Directions): Directions =
        when (direction) {
            Directions.LEFT -> Directions.RIGHT
            Directions.RIGHT -> Directions.LEFT
            Directions.UP -> Directions.DOWN
            Directions.DOWN -> Directions.UP
        }

}