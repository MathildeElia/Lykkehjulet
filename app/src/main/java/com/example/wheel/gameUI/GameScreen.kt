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
import androidx.navigation.NavController
import com.example.wheel.Navigation
import com.example.wheel.model.Letter
import com.example.wheel.ui.theme.WheelTheme

val viewmodel: Viewmodel = Viewmodel()

@Composable
fun GameScreen(navController: NavController) {
    Layout(navController)
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
fun Layout(navController: NavController) {

    Surface(

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

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                if(!viewmodel.isStarted){
                    viewmodel.findCurrentWord()
                    viewmodel.isStarted = true
                }
                Row(){
                    PointsMessage()
                    Spacer(modifier = Modifier.width(200.dp))
                    ShowLives()
                }
                Message()
                ShowCategory()
                BuildWord()
                ValueMessage()
                Spacer(modifier = Modifier.height(40.dp))
                SpinButton(navController)
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

        //LazyVerticalGrid code has been taken from Jetpack Compose Plaground and then modified.
        //Link: https://foso.github.io/Jetpack-Compose-Playground/foundation/lazyverticalgrid/
        LazyVerticalGrid(
            cells = GridCells.Adaptive(100.dp),

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
                border(BorderStroke(2.dp, Color.Magenta))
            }
    ) {
        Text(viewmodel.printLetter(letter))
    }
}

@Composable
fun SpinButton(navController : NavController) {
    val text = remember {mutableStateOf("!Spin!" )}
    var textSize = 40
    if(viewmodel.isGameOver()){
        textSize = 20
        text.value = "Play Again"
    }
    Button(
        onClick = {
            if(!viewmodel.isGameOver()){
                viewmodel.spinClicked()
            }
            else{
                navController.navigate(Screen.StartScreen.route)
            }
        }, shape = RoundedCornerShape(40.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta),
        modifier = Modifier
            .height(80.dp)
            .width(200.dp)
    )

    {
        Text(
            text = text.value, color = Color.White,
            fontSize = textSize.sp
        )
    }
}


@Composable
fun ShowCategory() {
    val curCategory by viewmodel.currentCategory
    Text("Category is: $curCategory", fontSize = 20.sp)
}

@Composable
fun ValueMessage() {
    val value by viewmodel.spinValueString
    if(!viewmodel.isGameOver()){
        Text(value, fontSize = 25.sp)
    }
    else{
        Text(viewmodel.endMessage.value, fontSize = 30.sp)
    }
}

@Composable
fun PointsMessage() {
    val currentPoints by viewmodel.points
    Text("Points: $currentPoints", fontSize = 25.sp, textAlign = TextAlign.Start)
}

@Composable
fun ShowLives() {
    val currentLives by viewmodel.lives
    Text("$currentLives :Lives", fontSize = 25.sp, textAlign = TextAlign.End)
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
        Navigation()
    }
}
