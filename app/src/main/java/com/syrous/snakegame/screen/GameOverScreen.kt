package com.syrous.snakegame.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

sealed class GameOverScreenAction {
    data object ReStart : GameOverScreenAction()
}


class GameOver(private val performAction: (GameOverScreenAction) -> Unit) {

    @Composable
    fun Screen(modifier: Modifier = Modifier) {

    }
}