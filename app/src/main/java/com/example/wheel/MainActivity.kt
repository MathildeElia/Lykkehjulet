package com.example.wheel

import Layout
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wheel.ui.theme.WheelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val viewmodel: Viewmodel() by viewModels()
        setContent {
            WheelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Layout()
                }
            }
        }
    }
}

/*
@Composable
fun buttonExample() {
    Box() {
        Image(painter = painterResource(id = R.drawable.knap), contentDescription = null,
            modifier = Modifier.clickable { println("Button Clicked!") })
    }
}*/

val values = arrayOf(0, 500, 600, 700, 800, 900, 10000)
val spinValue = mutableStateOf("")
val points = mutableStateOf(0)
val currentWord = mutableStateOf("")
val allWords: Array<Array<String>> = arrayOf(
    arrayOf("Around the House", "Rhyme Time", "On The Map", "Occupation"),
    arrayOf("DINING ROOM TABLE", "YUMMY IN MY TUMMY", "QUEBEC", "AUTO MECHANIC")
)
val letters = arrayOf(
    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
    "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
)
val guessedLettersList = mutableStateOf("")
val lettersInWord = mutableListOf<Letter>()

//opstilling af ord med tomme firkanter (ikke gættet)
//og viste bogstaver (gættet)
//hvis bogstav er i ord, så find fundet bogstav og gør dem synlige.

