package com.syrous.snakegame

import android.util.Log
import com.syrous.snakegame.SnakeEvent.SnakeHitWall
import com.syrous.snakegame.util.plus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow


class SnakeGridStateImpl : SnakeGridState {

    // Private variables
    private var gridHeight = 0
    private var gridWidth = 0
    private var snakeLength = 10
    private var preHeadDirection: Directions = Directions.RIGHT
    private var restrictedDirection: Directions = getOppositeDirection(Directions.RIGHT)

    override val snakeEvent = MutableSharedFlow<SnakeEvent>()
    override val snakeGrid = MutableStateFlow<List<Pair<Int, Int>>>(listOf())
    override val foodGrid = MutableStateFlow<List<Pair<Int, Int>>>(listOf())

    override fun updateGridSize(width: Int, height: Int) {
        if (gridWidth != width && gridHeight != height) {
            gridWidth = width
            gridHeight = height
            Log.d("SnakeGridState", "updateGridSize -> height = $gridHeight, width = $gridWidth")
            drawSnake()
        }
    }

    private fun drawSnake() {
        snakeGrid.value = buildList {
            repeat(snakeLength) {
                add(gridWidth / 2 + it to gridHeight / 2)
            }
        }
    }

    override suspend fun updateSnakeBodyAfterLoop() {
        when (preHeadDirection) {
            Directions.LEFT -> moveSnakeLeft()
            Directions.RIGHT -> moveSnakeRight()
            Directions.UP -> moveSnakeUp()
            Directions.DOWN -> moveSnakeDown()
        }
    }

    private fun checkWallConstraint(head: Pair<Int, Int>): Boolean {
        return head.first !in 2..<gridWidth || head.second !in 2..<gridHeight
    }

    override suspend fun moveSnakeLeft() {
        val currList = snakeGrid.value.toMutableList()
        Log.d("SnakeGridState", "move Left -> currList -> $currList")
        val head = snakeGrid.value.first()
        val tail = snakeGrid.value.last()
        when {
            checkWallConstraint(head) -> {
                snakeEvent.emit(SnakeHitWall)
            }

            restrictedDirection != Directions.LEFT -> {
                val newHead = head + Directions.LEFT.move
                currList.add(0, newHead)
                currList.remove(tail)
                preHeadDirection = Directions.LEFT
                restrictedDirection = getOppositeDirection(Directions.LEFT)
                Log.d(
                    "SnakeGridState",
                    "move Left -> snakeGrid -> ${snakeGrid.value}, newHead -> $newHead, restrictedDirection -> $restrictedDirection"
                )
                snakeGrid.value = currList
            }
        }
    }

    override suspend fun moveSnakeRight() {
        val currList = snakeGrid.value.toMutableList()
        Log.d("SnakeGridState", "move Right -> currList -> $currList")
        val head = snakeGrid.value.first()
        val tail = snakeGrid.value.last()
        when {
            checkWallConstraint(head) -> {
                snakeEvent.emit(SnakeHitWall)
            }

            restrictedDirection != Directions.RIGHT -> {
                val newHead = head + Directions.RIGHT.move
                currList.add(0, newHead)
                currList.remove(tail)
                preHeadDirection = Directions.RIGHT
                restrictedDirection = getOppositeDirection(Directions.RIGHT)
                Log.d(
                    "SnakeGridState",
                    "move Right -> currList -> ${snakeGrid.value}, newHead -> $newHead, restrictedDirection -> $restrictedDirection"
                )
                snakeGrid.value = currList
            }
        }
    }

    override suspend fun moveSnakeUp() {
        val currList = snakeGrid.value.toMutableList()
        Log.d("SnakeGridState", "move Up -> currList -> $currList")
        val head = snakeGrid.value.first()
        val tail = snakeGrid.value.last()
        when {
            checkWallConstraint(head) -> {
                snakeEvent.emit(SnakeHitWall)
            }

            restrictedDirection != Directions.UP -> {
                val newHead = head + Directions.UP.move
                currList.add(0, newHead)
                currList.remove(tail)
                preHeadDirection = Directions.UP
                restrictedDirection = getOppositeDirection(Directions.UP)
                Log.d(
                    "SnakeGridState",
                    "move Up -> snakeGrid -> ${snakeGrid.value}, newHead -> $newHead, restrictedDirection -> $restrictedDirection"
                )
                snakeGrid.value = currList
            }
        }
    }

    override suspend fun moveSnakeDown() {
        val currList = snakeGrid.value.toMutableList()
        Log.d("SnakeGridState", "move Down -> currList -> $currList")
        val head = snakeGrid.value.first()
        val tail = snakeGrid.value.last()
        when {
            checkWallConstraint(head) -> {
                snakeEvent.emit(SnakeHitWall)
            }

            restrictedDirection != Directions.DOWN -> {
                val newHead = head + Directions.DOWN.move
                currList.add(0, newHead)
                currList.remove(tail)
                preHeadDirection = Directions.DOWN
                restrictedDirection = getOppositeDirection(Directions.DOWN)
                Log.d(
                    "SnakeGridState",
                    "move Down -> currList -> ${snakeGrid.value}, newHead -> $newHead, restrictedDirection -> $restrictedDirection"
                )
                snakeGrid.value = currList
            }

        }
    }

    private fun getOppositeDirection(direction: Directions): Directions = when (direction) {
        Directions.LEFT -> Directions.RIGHT
        Directions.RIGHT -> Directions.LEFT
        Directions.UP -> Directions.DOWN
        Directions.DOWN -> Directions.UP
    }

}

sealed class SnakeEvent {
    data object SnakeHitWall : SnakeEvent()
}