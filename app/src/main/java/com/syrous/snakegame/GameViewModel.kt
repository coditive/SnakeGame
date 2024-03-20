package com.syrous.snakegame

import com.syrous.snakegame.screen.GameScreen
import kotlinx.coroutines.flow.StateFlow


interface GameViewModel {
    val currentScreen: StateFlow<GameScreen>
}