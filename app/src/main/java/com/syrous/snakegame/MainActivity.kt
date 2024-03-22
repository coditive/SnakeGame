package com.syrous.snakegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.syrous.snakegame.screen.GameOver
import com.syrous.snakegame.screen.GameOverScreenAction
import com.syrous.snakegame.screen.GamePlay
import com.syrous.snakegame.screen.GamePlayScreenAction
import com.syrous.snakegame.screen.GameScreen
import com.syrous.snakegame.screen.GameStart
import com.syrous.snakegame.screen.StartScreenAction
import com.syrous.snakegame.ui.theme.SnakeGameTheme

class MainActivity : ComponentActivity() {
    //Screen
    private lateinit var gameStart: GameStart
    private lateinit var gamePlay: GamePlay
    private lateinit var gameOver: GameOver

    //viewModel and Controllers
    private val viewModel: GameViewModel by viewModels<MainViewModelImpl>()
    private val controller: GameController by viewModels<MainViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnakeGameTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoadScreens()
                    val screen = viewModel.currentScreen.collectAsState().value
                    when (screen) {
                        GameScreen.START_GAME -> gameStart.Screen()
                        GameScreen.GAME_PLAY_SCREEN -> gamePlay.Screen()
                        GameScreen.GAME_OVER_SCREEN -> gameOver.Screen()
                    }

                }
            }
        }
    }

    @Composable
    fun LoadScreens() {
        gameStart = GameStart { action ->
            when(action) {
                StartScreenAction.Exit -> controller.exitGame()
                StartScreenAction.Start -> controller.startGame()
            }
        }
        gamePlay = GamePlay(controller.snakeGridState) { action ->
            when(action) {
                GamePlayScreenAction.MoveDown -> controller.moveDown()
                GamePlayScreenAction.MoveLeft -> controller.moveLeft()
                GamePlayScreenAction.MoveRight -> controller.moveRight()
                GamePlayScreenAction.MoveUp -> controller.moveUp()
                GamePlayScreenAction.EndGame -> controller.exitGame()
                GamePlayScreenAction.RestartGame -> controller.restartGame()
            }
        }
        gameOver = GameOver(controller.snakeGridState) { action ->
            when(action) {
                GameOverScreenAction.Exit -> finish()
            }
        }
    }

}