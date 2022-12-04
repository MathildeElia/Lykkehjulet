package com.example.wheel.gameUI

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wheel.Navigation
import com.example.wheel.ui.theme.WheelTheme

@Composable
fun StartScreen(navController: NavController) {

    Surface(
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
            // on below line we are
            // calling pop window dialog
            // method to display ui.
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Button(onClick = {
                    viewmodel.gameInit()
                    navController.navigate(Screen.GameScreen.route)
                }) {
                    Text(text = "Start Game! :)")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    WheelTheme {
        Navigation()
    }
}
