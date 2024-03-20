package com.syrous.snakegame.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

sealed class StartScreenAction {
    data object Start : StartScreenAction()
    data object Exit : StartScreenAction()
}

class GameStart(
    private val performAction: (StartScreenAction) -> Unit
) {

    @Composable
    fun Screen(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { performAction(StartScreenAction.Start) }) {
                Text(text = "Start Game")
            }

            Button(onClick = { performAction(StartScreenAction.Exit) }) {
                Text(text = "End Game")
            }
        }
    }
}