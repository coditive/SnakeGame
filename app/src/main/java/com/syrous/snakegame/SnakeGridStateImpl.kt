package com.syrous.snakegame

import android.util.Log
import com.syrous.snakegame.SnakeEvent.SnakeHitWall
import com.syrous.snakegame.util.plus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.random.Random.Default.nextInt


class SnakeGridStateImpl : SnakeGridState {

    // Private variables
    private var gridHeight = 0
    private var gridWidth = 0
    private var snakeLength = 10
    private var preHeadDirection: Directions = Directions.LEFT
    private var restrictedDirection: Directions = getOppositeDirection(Directions.LEFT)

    override val snakeEvent = MutableSharedFlow<SnakeEvent>()
    override val snakeGrid = MutableStateFlow<List<Pair<Int, Int>>>(listOf())
    override val foodGrid = MutableStateFlow<List<Pair<Int, Int>>>(listOf())
    override val score = MutableStateFlow(0)

    override fun updateGridSize(width: Int, height: Int) {
        if (gridWidth != width && gridHeight != height) {
            gridWidth = width
            gridHeight = height
            Log.d("SnakeGridState", "updateGridSize -> height = $gridHeight, width = $gridWidth")
            populateFoodInGame()
            drawSnake()
        }
    }

    private fun populateFoodInGame() {
        foodGrid.value = buildList {
            repeat(50) {
                add(generateFood())
            }
        }
    }

    private fun generateFood(): Pair<Int, Int> {
        return nextInt(gridWidth) to nextInt(gridHeight)
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

    private fun canHaveFood(head: Pair<Int, Int>): Pair<Int, Int>? {
        for (food in foodGrid.value) {
            val foodRangeX = food.first - 1 until food.first + 1
            val foodRangeY = food.second - 1 until food.second + 1
            if (head.first in foodRangeX && head.second in foodRangeY) {
                return food
            }
        }
        return null
    }

    override suspend fun moveSnakeLeft() {
        val currList = snakeGrid.value.toMutableList()
        val foodList = foodGrid.value.toMutableList()
        Log.d("SnakeGridState", "move Left -> currList -> $currList")
        val head = snakeGrid.value.first()
        val newHead = head + Directions.LEFT.move
        val canHaveFood = canHaveFood(newHead)
        when {
            checkWallConstraint(head) -> {
                snakeEvent.emit(SnakeHitWall)
            }

            canHaveFood != null -> {
                currList.add(0, newHead)
                preHeadDirection = Directions.LEFT
                restrictedDirection = getOppositeDirection(Directions.LEFT)
                foodList.remove(canHaveFood)
                score.value += 1
                snakeGrid.value = currList
                foodGrid.value = foodList
            }

            restrictedDirection != Directions.LEFT -> {
                currList.add(0, newHead)
                preHeadDirection = Directions.LEFT
                restrictedDirection = getOppositeDirection(Directions.LEFT)
                Log.d(
                    "SnakeGridState",
                    "move Left -> currList -> ${currList}, newHead -> $newHead, restrictedDirection -> $restrictedDirection"
                )
                snakeGrid.value = currList.dropLast(1)
            }
        }
    }

    override suspend fun moveSnakeRight() {
        val currList = snakeGrid.value.toMutableList()
        val foodList = foodGrid.value.toMutableList()
        Log.d("SnakeGridState", "move Right -> currList -> $currList")
        val head = snakeGrid.value.first()
        val newHead = head + Directions.RIGHT.move
        val canHaveFood = canHaveFood(newHead)
        when {
            checkWallConstraint(head) -> {
                snakeEvent.emit(SnakeHitWall)
            }

            canHaveFood != null -> {
                currList.add(0, newHead)
                preHeadDirection = Directions.RIGHT
                restrictedDirection = getOppositeDirection(Directions.RIGHT)
                foodList.remove(canHaveFood)
                score.value += 1
                snakeGrid.value = currList
                foodGrid.value = foodList
            }

            restrictedDirection != Directions.RIGHT -> {
                currList.add(0, newHead)
                preHeadDirection = Directions.RIGHT
                restrictedDirection = getOppositeDirection(Directions.RIGHT)
                Log.d(
                    "SnakeGridState",
                    "move Right -> currList -> ${currList}, newHead -> $newHead, restrictedDirection -> $restrictedDirection"
                )
                snakeGrid.value = currList.dropLast(1)

            }
        }
    }

    override suspend fun moveSnakeUp() {
        val currList = snakeGrid.value.toMutableList()
        val foodList = foodGrid.value.toMutableList()
        Log.d("SnakeGridState", "move Up -> currList -> $currList")
        val head = snakeGrid.value.first()
        val newHead = head + Directions.UP.move
        val canHaveFood = canHaveFood(newHead)
        when {
            checkWallConstraint(head) -> {
                snakeEvent.emit(SnakeHitWall)
            }

            canHaveFood != null -> {
                currList.add(0, newHead)
                preHeadDirection = Directions.UP
                restrictedDirection = getOppositeDirection(Directions.UP)
                foodList.remove(canHaveFood)
                score.value += 1
                snakeGrid.value = currList
                foodGrid.value = foodList
            }

            restrictedDirection != Directions.UP -> {
                currList.add(0, newHead)
                preHeadDirection = Directions.UP
                restrictedDirection = getOppositeDirection(Directions.UP)
                snakeGrid.value = currList.dropLast(1)

            }
        }
    }

    override suspend fun moveSnakeDown() {
        val currList = snakeGrid.value.toMutableList()
        val foodList = foodGrid.value.toMutableList()
        Log.d("SnakeGridState", "move Down -> currList -> $currList")
        val head = snakeGrid.value.first()
        val newHead = head + Directions.DOWN.move
        val canHaveFood = canHaveFood(newHead)
        when {
            checkWallConstraint(head) -> {
                snakeEvent.emit(SnakeHitWall)
            }

            canHaveFood != null -> {
                currList.add(0, newHead)
                preHeadDirection = Directions.DOWN
                restrictedDirection = getOppositeDirection(Directions.DOWN)
                foodList.remove(canHaveFood)
                score.value += 1
                snakeGrid.value = currList
                foodGrid.value = foodList
            }

            restrictedDirection != Directions.DOWN -> {
                currList.add(0, newHead)
                preHeadDirection = Directions.DOWN
                restrictedDirection = getOppositeDirection(Directions.DOWN)
                Log.d(
                    "SnakeGridState",
                    "move Down -> currList -> ${currList}, newHead -> $newHead, restrictedDirection -> $restrictedDirection"
                )
                snakeGrid.value = currList.dropLast(1)

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