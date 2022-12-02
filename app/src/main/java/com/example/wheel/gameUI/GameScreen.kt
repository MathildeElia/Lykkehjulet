package com.example.wheel.gameUI

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.wheel.*
import com.example.wheel.State
import com.example.wheel.ui.theme.WheelTheme
import kotlin.system.exitProcess

val viewmodel: Viewmodel = Viewmodel()

@Composable
fun GameScreen(navController: NavController) {
    Layout()
}

@Composable
fun Keyboard() {
    viewmodel.addValuesToKeyboard()

    Column(
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(modifier = Modifier.padding(18.dp, 0.dp, 0.dp, 0.dp)) {
            for (i in 0..7) {
                InsertLetterInKeyboard(i = i)
            }
        }
        Row() {
            for (i in 8..16) {
                InsertLetterInKeyboard(i = i)
            }
        }
        Row() {
            for (i in 17..25) {
                InsertLetterInKeyboard(i = i)
            }
        }
    }
}

@Composable
fun InsertLetterInKeyboard(i: Int) {
    val color = remember { mutableStateOf(Color.Magenta) }

    Button(
        //colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta),
        onClick = {
            if (viewmodel.pressKeyboardLetter(i)) {
                color.value = Color.LightGray
            }
        },
        contentPadding = PaddingValues(
            start = 1.dp,
            top = 1.dp,
            end = 1.dp,
            bottom = 1.dp,
        ),
        shape = RoundedCornerShape(1.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = color.value),
        modifier = Modifier
            .size(43.dp)
            .padding(4.dp),
    )

    {
        Text(
            text = viewmodel.keyboardLetters[i].letter,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            //modifier = Modifier.offset(0.dp, (-3).dp)
        )
    }
}

@Composable
fun Layout() {

    // on below line we
    // are specifying background color
    // for our application
    Surface(
        // on below line we are specifying modifier and color for our app
        modifier = Modifier.fillMaxSize(), color = Color.Yellow
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
                PointsMessage()
                ShowLives()
                Message()
                ShowCategory()
                BuildWord()
                ValueMessage()
                Spacer(modifier = Modifier.height(40.dp))
                Text("Guessed: ${viewmodel.guessedLettersList.value}")
                SpinButton()
                Spacer(modifier = Modifier.height(40.dp))
                Keyboard()

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BuildWord() {
    val list by remember { mutableStateOf(viewmodel.lettersInWord) }
    Column() {
        //prøv spacer
        LazyVerticalGrid(
            cells = GridCells.Adaptive(100.dp),

            // content padding
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
            content = {
                items(list.size) { index ->
                    Card(
                        backgroundColor = Color.Magenta,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        elevation = 8.dp,
                    ) {
                        Text(
                            text = viewmodel.printLetter(list[index]),
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                            //modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        )
    }
}

fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}

@Composable
fun InsertLetterInWord(letter: Letter) {
    Box(
        Modifier
            .size(30.dp)
            .padding(2.dp)
            .conditional(letter.letter != " ") {
                border(BorderStroke(2.dp, Color.Red))
            }
    ) {
        Text(viewmodel.printLetter(letter))
    }
}

@Composable
fun SpinButton() {

    Button(
        onClick = {
            viewmodel.spinClicked()
        }, shape = RoundedCornerShape(40.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta),
        modifier = Modifier
            .height(80.dp)
            .width(200.dp)
    )

    {
        Text(
            text = "!Spin!", color = Color.White,
            fontSize = 40.sp
        )
    }
}


@Composable
fun ShowCategory() {
    Text("Category is: " + viewmodel.findCurrentWord(), fontSize = 20.sp)
}

@Composable
fun ValueMessage() {
    val value by viewmodel.spinValueString
    Text(value, fontSize = 25.sp)
}

@Composable
fun PointsMessage() {
    val currentPoints by viewmodel.points
    Text("Points: $currentPoints", fontSize = 25.sp)
}

@Composable
fun ShowLives() {
    val currentLives by viewmodel.lives
    Text("Lives: $currentLives", fontSize = 25.sp)
}


@Composable
fun Message() {
    Text(
        "Press the button to spin the wheel!",
        fontSize = 25.sp
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WheelTheme {
        Layout()
    }
}
