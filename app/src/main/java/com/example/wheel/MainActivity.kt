package com.example.wheel

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

@Composable
fun Keyboard() {
        Column() {
            Row(modifier = Modifier.padding(18.dp,0.dp,0.dp,0.dp)) {
                for (i in 0..7) {
                    InsertLetter(i = i)
                }
            }
            Row() {
                for (i in 8..16) {
                    InsertLetter(i = i)
                }
            }
            Row() {
                for (i in 17..25) {
                    InsertLetter(i = i)
                }
            }
        }
}

fun guessedLetters(letter: String): Int {
    var counter = 0

    for (l in lettersInWord){
        if(l.letter == letter){
            l.makeVisible()
            counter++
        }
    }
    return counter
}



@Composable
fun InsertLetter(i : Int) {
    val hasBeenPressed =  remember {mutableStateOf(false)}
    val color = remember {mutableStateOf(Color.Magenta)}

    Button(
        onClick = {
            if(!hasBeenPressed.value){
                hasBeenPressed.value = true
                color.value = Color.LightGray

                guessedLetters(letters[i])
                guessedLettersList.value += letters[i]

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
            .size(35.dp)
            .padding(2.dp),
    )

    {
        Text(
            text = letters[i],
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier.offset(0.dp, (-3).dp)
        )
    }
}

@Composable
fun FindCurrentWord() {
    val ran = (0..3).random()
    currentWord.value = allWords[1][ran]
    Text("Category is: " + allWords[0][ran], fontSize = 20.sp)

    //make letter objects
    for(letter in currentWord.value){
        lettersInWord.add(Letter(letter.toString()))
    }
}

@Composable
fun Word() {
    Row() {
        Text(
            buildAnnotatedString {
                for(letter in lettersInWord){
                    ChangeLetterVisibility(letter = letter)
                }
            }//, style = TextStyle(textDecoration = TextDecoration.Underline, color = Color.Black)
        )
    }
}

//Forsøg på at gøre bogstav synligt.
@Composable
fun ChangeLetterVisibility(letter: Letter) {
    var visible by remember { mutableStateOf(letter.isVisible.value) }

    Box(
        Modifier.size(30.dp).padding(2.dp).border(BorderStroke(2.dp, Color.Red))
    ){
        if(visible){
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ), //scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),

                modifier = Modifier.border(BorderStroke(2.dp, Color.Red))
            ) {
                Text(
                    letter.letter,
                    Modifier
                        .size(30.dp)
                )
            }
        }
    }

}

//starter med false. Funktion tager imod tal og bogstav.
//Laver en boks med bogstavet. Ligesom tastaturet.
//Funktionen bruges først til at lave en streng fyldt med mellemrum
//Bagefter sættes bogstavet som der er gættet på ind.
//Hvis bogstavet er i currentword, sættes bogstavet ind i boksen på
//dens tilsvarende plads. Hvis ikke (den er false) så mister man et liv.



@Composable
fun SpinButton() {

    Button(
        onClick = {
            //From first click and onwards, s
            val ran = (0..6).random()
            val newSpinValue = values[ran]
            if (newSpinValue == 0) {
                spinValue.value = "Bankrupt!"
                points.value = 0
            } else {
                spinValue.value = "You got $newSpinValue points!"
                //det her skal ikke være her, men når der bliver gættet.
                points.value += newSpinValue
            }
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
fun ValueMessage() {
    val value by spinValue
    Text(value, fontSize = 25.sp)
}

@Composable
fun PointsMessage() {
    val currentPoints by points
    Text("Points: $currentPoints", fontSize = 25.sp)
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
        PointsMessage()
        Message()
        FindCurrentWord()
        Word()
        ValueMessage()
        Spacer(modifier = Modifier.height(30.dp))
        Text("Guessed: ${guessedLettersList.value}")
        SpinButton()
        Keyboard()
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WheelTheme {
        Layout()
    }
}
/*
Composable
fun Letter(letter: String) {
    Surface(
        modifier = Modifier.size(30.dp, 40.dp),
        color = Color.LightGray,
        shape = RoundedCornerShape(size = 8.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = letter,
                color = Color.Black,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
        }
    }
} */

/*
Composable
fun BuildWord(word: Word) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(9),
        content = {

            itemsIndexed(word.letters) { index, letter ->
                val foundWord by remember { mutableStateOf(letter) }
                if (foundWord.isFound.value) Letter(letter.letter) else Letter("")
            }
        },
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp),

        )
} */