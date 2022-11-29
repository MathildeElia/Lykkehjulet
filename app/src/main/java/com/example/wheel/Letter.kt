package com.example.wheel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class Letter constructor(val letter: String){
    private val _isVisible = mutableStateOf(false)
    val isVisible: State<Boolean> = _isVisible

    fun makeVisible(){
        _isVisible.value = true
    }

}