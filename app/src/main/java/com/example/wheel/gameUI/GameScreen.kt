import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wheel.*
import com.example.wheel.gameUI.Viewmodel
import com.example.wheel.ui.theme.WheelTheme

val viewmodel: Viewmodel = Viewmodel()

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

@Composable
fun InsertLetter(i : Int) {
    val hasBeenPressed =  remember { mutableStateOf(false) }

    val color = remember { mutableStateOf(Color.Magenta) }

    Button(
        onClick = {
            if(viewmodel.pressKeyboardLetter(i)){
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
            .size(35.dp)
            .padding(2.dp),
    )

    {
        Text(
            text = viewmodel.letters[i],
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier.offset(0.dp, (-3).dp)
        )
    }

    if (hasBeenPressed.value){
        //gæt her
        guessedLetters(letter = viewmodel.letters[i])
    }
}

@Composable
fun ShowCategory() {
    Text("Category is: " + viewmodel.findCurrentWord(), fontSize = 20.sp)
}

@Composable
fun guessedLetters(letter: String): Int {
    var counter = 0

    for (l in viewmodel.lettersInWord){
        if(l.letter == letter){
            l.makeVisible()
            //ChangeLetterVisibility(letter = l)
            counter++
        }
    }
    return counter
}

@Composable
fun Word() {
    Row() {
        Text(
            buildAnnotatedString {
                for(letter in viewmodel.lettersInWord){
                    ChangeLetterVisibility(letter = letter)
                }
            }//, style = TextStyle(textDecoration = TextDecoration.Underline, color = Color.Black)
        )
    }
}

fun Modifier.conditional(condition : Boolean, modifier : Modifier.() -> Modifier) : Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}

//Forsøg på at gøre bogstav synligt.
@Composable
fun ChangeLetterVisibility(letter: Letter) {
    //val curLetter by remember { mutableStateOf(letter) }

    Box(
        Modifier
            .size(30.dp)
            .padding(2.dp)
            .conditional(letter.letter != " "){
                border(BorderStroke(2.dp, Color.Red))
            }
    ){
        Text(viewmodel.printLetter(letter))
        /*
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
        }*/
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
fun ValueMessage() {
    val value by viewmodel.spinValue
    Text(value, fontSize = 25.sp)
}

@Composable
fun PointsMessage() {
    val currentPoints by viewmodel.points
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
        ShowCategory()
        Word()
        ValueMessage()
        Spacer(modifier = Modifier.height(30.dp))
        Text("Guessed: ${viewmodel.guessedLettersList.value}")
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