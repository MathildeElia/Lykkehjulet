package com.example.wheel
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wheel.gameUI.GameScreen
import com.example.wheel.gameUI.StartScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController,Screen.StartScreen.route){
        composable(Screen.StartScreen.route){
            StartScreen(navController)
        }
        composable(Screen.GameScreen.route){
            GameScreen(navController)
        }
    }
}