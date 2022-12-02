sealed class Screen (val route:String){
    object StartScreen : Screen("start-screen")
    object GameScreen : Screen("game-screen")
    object EndScreen : Screen("end-screen")

}