package com.example.wheel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class KeyboardLetter constructor(val letter: String){
    private val _isPressed = mutableStateOf(false)
    var isPressed: State<Boolean> = _isPressed

    fun pressLetter(){
        _isPressed.value = true
    }

}