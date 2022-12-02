package com.example.wheel.gameUI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wheel.MainActivity
import kotlin.system.exitProcess

@Composable
fun EndScreen(navController: NavController) {
    Surface(
        // on below line we are specifying modifier and color for our app
        modifier = Modifier.fillMaxSize()
    ) {

        Scaffold(
            topBar = {
                TopAppBar(backgroundColor = Color.Magenta,
                    title = {
                        Text(
                            text = "Wheel of Fortune",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.White

                        )
                    })
            }) {

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Button(
                    onClick = {
                        navController.navigate(Screen.GameScreen.route)
                    }, shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta),
                    modifier = Modifier
                        .height(80.dp)
                        .width(200.dp)
                )
                {
                    Text(text = "Play Again")
                }
            }
        }
    }
}

@Composable
fun EndGameButton() {
    // on below line we are creating a column,
    Column(
    ) {
        // on below line creating a button
        // to close application
        Button(onClick = {
            // on below line we are creating and
            // initializing variable for activity
            val activity = MainActivity()
            // on below line we are finishing activity.
            activity.finish()
            exitProcess(0)

        }) {
            Text(text = "Close Game")
        }
    }
}