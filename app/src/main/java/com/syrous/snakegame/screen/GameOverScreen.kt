package com.syrous.snakegame.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.syrous.snakegame.SnakeGridState

sealed class GameOverScreenAction {
    data object Exit : GameOverScreenAction()
}


class GameOver(
    private val snakeGridState: SnakeGridState,
    private val performAction: (GameOverScreenAction) -> Unit
) {

    @Composable
    fun Screen(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val score = snakeGridState.score.collectAsState(initial = 0).value
            Text(text = "Your Final Score", fontSize = 48.sp)
            ScoreTicker(
                modifier = modifier
                    .wrapContentWidth()
                    .wrapContentHeight(), score = score
            )
            Button(onClick = { performAction(GameOverScreenAction.Exit) }) {
                Text(text = "Exit Game")
            }
        }
    }

    @Composable
    fun ScoreTicker(modifier: Modifier, score: Int) {
        Row(modifier = modifier) {
            score.toString().forEach { digit ->
                AnimatedContent(modifier = modifier,
                    targetState = digit,
                    label = "",
                    transitionSpec = {
                        if (targetState > initialState) {
                            slideInVertically { -it } togetherWith slideOutVertically { it }
                        } else {
                            slideInVertically { it } togetherWith slideOutVertically { -it }
                        }
                    }) {
                    Text(
                        text = it.toString(),
                        fontSize = 56.sp,
                        modifier = Modifier,
                    )
                }
            }
        }
    }

}