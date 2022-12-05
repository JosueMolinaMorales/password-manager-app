package com.example.password_manager_app.ui.app.records.components.generate_password

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class GeneratePasswordViewModel: ViewModel() {
    private val _sliderPosition: MutableState<Int> = mutableStateOf(8)
    val sliderPosition: State<Int> = _sliderPosition

    private val _includeCapitalLetters: MutableState<Boolean> = mutableStateOf(true)
    val includeCapitalLetters: State<Boolean> = _includeCapitalLetters

    private val _includeNumbers: MutableState<Boolean> = mutableStateOf(true)
    val includeNumbers = _includeNumbers

    private val _includeSpecialCharacters: MutableState<Boolean> = mutableStateOf(true)
    val includeSpecialCharacters = _includeSpecialCharacters

    fun setSliderPosition(sliderPosition: Float) {
        _sliderPosition.value = sliderPosition.toInt()
    }

    fun setCapitalLettersChoice(includeCapitalLetters: Boolean) {
        _includeCapitalLetters.value = includeCapitalLetters
    }

    fun setNumbersChoice(includeNumbers: Boolean) {
        _includeNumbers.value = includeNumbers
    }

    fun setSpecialCharacterChoice(includeSpecialCharacters: Boolean) {
        _includeSpecialCharacters.value = includeSpecialCharacters
    }

    fun generatePassword(): String {
        var characters = "abcdefghijklmnopqrstuvwxyz"
        val capitalLetters = characters.uppercase()
        val numbers = "0123456789"
        val specialCharacters = "!@#$%^&*()_+[{]}\\|;:'\",<.>/?"

        if (_includeSpecialCharacters.value) {
            characters += specialCharacters
        }
        if (_includeCapitalLetters.value) {
            characters += capitalLetters
        }
        if (_includeNumbers.value) {
            characters += numbers
        }

        var password = ""
        for (i in 1.._sliderPosition.value) {
            password += characters.random()
        }
        return password
    }
}