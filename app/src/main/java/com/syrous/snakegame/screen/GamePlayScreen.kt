package com.syrous.snakegame.screen

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.syrous.snakegame.R
import com.syrous.snakegame.SnakeEvent
import com.syrous.snakegame.SnakeGridState
import com.syrous.snakegame.screen.GamePlayScreenAction.RestartGame
import com.syrous.snakegame.util.FoodSize
import com.syrous.snakegame.util.SnakeSize
import com.syrous.snakegame.util.UnitScale
import kotlinx.coroutines.flow.collectLatest

sealed class GamePlayScreenAction {
    data object MoveLeft : GamePlayScreenAction()
    data object MoveRight : GamePlayScreenAction()
    data object MoveUp : GamePlayScreenAction()
    data object MoveDown : GamePlayScreenAction()
    data object RestartGame : GamePlayScreenAction()
    data object EndGame : GamePlayScreenAction()
}


class GamePlay(
    private val snakeGridState: SnakeGridState,
    private val performAction: (GamePlayScreenAction) -> Unit
) {

    @Composable
    fun Screen(modifier: Modifier = Modifier) {
        Scaffold(
            modifier = modifier,
            topBar = {
                val score = snakeGridState.score.collectAsState().value
                Text(text = "Your Score: $score")
            }
        ) {
            var isGameOver by remember {
                mutableStateOf(false)
            }

            LaunchedEffect(key1 = Unit) {
                snakeGridState.snakeEvent.collectLatest { event ->
                    when (event) {
                        SnakeEvent.SnakeHitWall -> {
                            isGameOver = true
                        }
                    }
                }
            }

            val snakePosition = snakeGridState.snakeGrid.collectAsState().value
            val foodPosition = snakeGridState.foodGrid.collectAsState().value

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(10.dp),
            ) {
                GridCanvas(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
                        .border(
                            width = 1.dp,
                            color = Color.Gray
                        ),
                    snakePosition = snakePosition,
                    foodPosition = foodPosition
                )

                if (!isGameOver) {
                    GridController(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        performAction = performAction
                    )
                } else {
                    ActionButtons(
                        Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) { action ->
                        if (action == GamePlayScreenAction.EndGame) {
                            performAction(GamePlayScreenAction.EndGame)
                        } else if (action == RestartGame) {
                            isGameOver = false
                            performAction(GamePlayScreenAction.RestartGame)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun GridCanvas(
        modifier: Modifier = Modifier,
        snakePosition: List<Pair<Int, Int>>,
        foodPosition: Set<Pair<Int, Int>>
    ) {
        Canvas(modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                snakeGridState.updateGridSize(
                    coordinates.size.width / UnitScale,
                    coordinates.size.height / UnitScale
                )
            }) {
            Log.d("GamePlayScreen", "snakePosition -> $snakePosition")
            for (pos in snakePosition) {
                drawRect(
                    color = Color.Red,
                    topLeft = Offset(
                        pos.first.toFloat() * UnitScale,
                        pos.second.toFloat() * UnitScale
                    ),
                    size = Size(SnakeSize.dp.toPx(), SnakeSize.dp.toPx()),
                )
            }
            Log.d("GamePlayScreen", "foodPosition -> $foodPosition")

            for (food in foodPosition) {
                drawCircle(
                    color = Color.Black,
                    radius = FoodSize.dp.toPx(),
                    center = Offset(
                        food.first.toFloat() * UnitScale,
                        food.second.toFloat() * UnitScale
                    )
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
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { performAction(GamePlayScreenAction.MoveUp) }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_upward_24),
                        contentDescription = ""
                    )
                }
            }
            Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { performAction(GamePlayScreenAction.MoveLeft) }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = ""
                    )
                }

                Spacer(modifier = Modifier.size(8.dp))

                Button(onClick = { performAction(GamePlayScreenAction.MoveRight) }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
                        contentDescription = ""
                    )
                }
            }
            Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { performAction(GamePlayScreenAction.MoveDown) }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_downward_24),
                        contentDescription = ""
                    )
                }
            }
        }
    }

    @Composable
    fun ActionButtons(
        modifier: Modifier = Modifier,
        performAction: (GamePlayScreenAction) -> Unit
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(onClick = { performAction(RestartGame) }) {
                Text(text = "Restart Game")
            }

            Button(onClick = { performAction(GamePlayScreenAction.EndGame) }) {
                Text(text = "End Game")
            }
        }
    }
}