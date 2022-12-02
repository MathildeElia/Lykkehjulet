package com.example.wheel.gameUI

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
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
fun GameScreen(navController: NavController){
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
    val color = remember { mutableStateOf(viewmodel.keyboardLetters[i].color.value) }

    Button(
        //colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta),
        onClick = {
            if (viewmodel.pressKeyboardLetter(i)) {
                viewmodel.keyboardLetters[i].color.value = Color.LightGray
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
fun winPopUp() {
    var restartIsPressed by remember {mutableStateOf(false)}

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
                if(!restartIsPressed){
                    Button(onClick = {
                        restartIsPressed = viewmodel.initGame()
                    }) {
                        Text(text = "Start Game! :)")
                    }
                }

                if(restartIsPressed){
                    PopupWindowDialog()
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
                    RestartButton()

                    restartIsPressed = false
                }
            }
        }
    }
}

@Composable
fun PopupWindowDialog() {
    // on below line we are creating variable for button title
    // and open dialog.
    val gameOver = remember { mutableStateOf(State.SPIN) }

    // on below line we are checking if dialog is open
    if (gameOver.value == State.WIN) {
        PopUp(popUpText = "You've Won!")
    }
    if (gameOver.value == State.LOST) {
        PopUp(popUpText = "You lost :( ")
    }
}

@Composable
fun PopUp(popUpText: String) {
// on below line we are specifying height and width
    val popupWidth = 300.dp
    val popupHeight = 100.dp
    // on below line we are adding pop up
    Popup(
        // on below line we are adding
        // alignment and properties.
        alignment = Alignment.TopCenter,
        properties = PopupProperties()
    ) {

        // on the below line we are creating a box.
        Box(
            // adding modifier to it.
            Modifier
                .size(popupWidth, popupHeight)
                .padding(top = 5.dp)
                // on below line we are adding background color
                .background(Color.Red, RoundedCornerShape(10.dp))
                // on below line we are adding border.
                .border(1.dp, color = Color.Black, RoundedCornerShape(10.dp))
        ) {

            // on below line we are adding column
            Column(
                // on below line we are adding modifier to it.
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                // on below line we are adding horizontal and vertical
                // arrangement to it.
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // on below line we are adding text for our pop up
                Text(
                    // on below line we are specifying text
                    text = popUpText,
                    // on below line we are specifying color.
                    color = Color.White,
                    // on below line we are adding padding to it
                    modifier = Modifier.padding(vertical = 5.dp),
                    // on below line we are adding font size.
                    fontSize = 16.sp
                )
                EndGameButton()
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

@Composable
fun RestartButton() {
    var isPressed by remember {mutableStateOf(false)}
    Column {

    }
    Button(
        {
            isPressed = true
        }
    ) {
        Text(text = "Restart")
    }
    if(isPressed){
        viewmodel.initGame()
    }
}




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BuildWord() {
    val list by remember { mutableStateOf(viewmodel.lettersInWord)}
    Column() {
        //prøv spacer
        LazyVerticalGrid(
            cells = GridCells.Fixed(9),

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
fun GameOverMessage() {
    val value by viewmodel.currentState

    Text(value.toString(), fontSize = 25.sp)
}

@Composable
fun ShowCategory() {
    val curCategory by viewmodel.currentCategory

    Text("Category is: $curCategory", fontSize = 20.sp)
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

@Composable
fun Layout() {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        winPopUp()
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WheelTheme {
        Layout()
    }
}
