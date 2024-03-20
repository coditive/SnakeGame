package com.syrous.snakegame.util


operator fun Pair<Int, Int>.plus(second: Pair<Int, Int>): Pair<Int, Int> {
    val sumA = this.first + second.first
    val sumB = this.second + second.second
    return Pair(sumA, sumB)
}