package com.syrous.snakegame.screen

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.syrous.snakegame.R
import com.syrous.snakegame.SnakeGridState
import com.syrous.snakegame.util.UnitScale

sealed class GamePlayScreenAction {
    data object MoveLeft : GamePlayScreenAction()
    data object MoveRight : GamePlayScreenAction()
    data object MoveUp : GamePlayScreenAction()
    data object MoveDown : GamePlayScreenAction()
}


class GamePlay(
    private val snakeGridState: SnakeGridState,
    private val performAction: (GamePlayScreenAction) -> Unit
) {

    @Composable
    fun Screen(modifier: Modifier = Modifier) {
        Surface(
            modifier = modifier,
        ) {
            val snakePosition = snakeGridState.snakeGrid.collectAsState().value
            Log.d("GamePlayScreen", "snakePosition after collection -> $snakePosition")
            GridCanvas(modifier, snakePosition)
            GridController(modifier, performAction)
        }
    }

    @Composable
    fun GridCanvas(modifier: Modifier = Modifier, snakePosition: List<Pair<Int, Int>>) {
        Canvas(modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                snakeGridState.updateGridSize(coordinates.size.width / UnitScale, coordinates.size.height / UnitScale)
            }) {
            Log.d("GamePlayScreen", "snakePosition -> $snakePosition")
            for (pos in snakePosition) {
                drawRect(
                    color = Color.Red,
                    topLeft = Offset(pos.first.toFloat() * UnitScale, pos.second.toFloat() * UnitScale),
                    size = Size(20.dp.toPx(), 20.dp.toPx()),
                )
            }
        }
    }

    @Composable
    fun GridController(
        modifier: Modifier = Modifier,
        performAction: (GamePlayScreenAction) -> Unit
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(modifier.fillMaxWidth()) {
                Button(onClick = { performAction(GamePlayScreenAction.MoveUp) }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_upward_24),
                        contentDescription = ""
                    )
                }
            }
            Row(modifier.fillMaxWidth()) {
                Button(onClick = { performAction(GamePlayScreenAction.MoveLeft) }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = ""
                    )
                }
                Button(onClick = { performAction(GamePlayScreenAction.MoveRight) }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
                        contentDescription = ""
                    )
                }
            }
            Row(modifier.fillMaxWidth()) {
                Button(onClick = { performAction(GamePlayScreenAction.MoveDown) }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_downward_24),
                        contentDescription = ""
                    )
                }
            }
        }
    }


}