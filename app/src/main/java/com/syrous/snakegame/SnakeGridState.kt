package com.syrous.snakegame

import android.util.Log
import com.syrous.snakegame.util.plus
import kotlinx.coroutines.flow.MutableStateFlow

class SnakeGridState {

    private var gridLength = 0
    private var gridWidth = 0
    private var snakeLength = 10
    var snakeGrid = MutableStateFlow<List<Pair<Int, Int>>>(listOf())
    val foodGrid = MutableStateFlow<List<Pair<Int, Int>>>(listOf())

    fun updateGridSize(width: Int, length: Int) {
       if(gridWidth != width && gridLength != length) {
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
        val newHead = head + Directions.LEFT.move
        if (!currList.contains(newHead)) {
            currList.add(0, newHead)
            currList.remove(tail)
            snakeGrid.value = currList
            Log.d("SnakeGridState", "move Left -> snakeGrid -> ${snakeGrid.value}, currList -> $currList, newHead -> $newHead, tail -> $tail")
        }
    }

    fun moveSnakeRight() {
        val currList = snakeGrid.value.toMutableList()
        Log.d("SnakeGridState", "move Right -> currList -> $currList")
        val head = snakeGrid.value.first()
        val tail = snakeGrid.value.last()
        val newHead = head + Directions.RIGHT.move
        if (!currList.contains(newHead)) {
            currList.add(newHead)
            currList.remove(tail)
            snakeGrid.value = currList
            Log.d("SnakeGridState", "move Right -> currList -> ${snakeGrid.value}, newHead -> $newHead, tail -> $tail")
        }
    }

    fun moveSnakeUp() {
        val currList = snakeGrid.value.toMutableList()
        val head = snakeGrid.value.first()
        val tail = snakeGrid.value.last()
        val newHead = head + Directions.UP.move
        if (!currList.contains(newHead)) {
            currList.add(newHead)
            currList.remove(tail)
            snakeGrid.value = currList
        }
    }

    fun moveSnakeDown() {
        val currList = snakeGrid.value.toMutableList()
        val head = snakeGrid.value.first()
        val tail = snakeGrid.value.last()
        val newHead = head + Directions.DOWN.move
        if (!currList.contains(newHead)) {
            currList.add(newHead)
            currList.remove(tail)
            snakeGrid.value = currList
        }
    }

}