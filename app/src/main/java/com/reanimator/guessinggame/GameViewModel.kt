package com.reanimator.guessinggame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val words = listOf("Android", "Activity", "Fragment") // possible words to guess
    private val secretWord = words.random().uppercase() // word to guess
    private var correctGuesses = "" // correct guess made

    private val _secretWordDisplay = MutableLiveData<String>()
    val secretWordDisplay: LiveData<String>
        get() = _secretWordDisplay // how the word is displayed

    private val _incorrectGuesses = MutableLiveData<String>("")
    val incorrectGuesses: LiveData<String>
        get() = _incorrectGuesses// incorrect guesses made

    private val _livesLeft = MutableLiveData<Int>(8) // number of lives left
    val livesLeft: LiveData<Int>
        get() = _livesLeft

    private val _gameOver = MutableLiveData<Boolean>(false)
    val gameOver: LiveData<Boolean>
        get() = _gameOver

    init {
        _secretWordDisplay.value = deriveSecretWordDisplay()
    }


    // this builds a string for how exactly should the secret word be displayed on the screen
    private fun deriveSecretWordDisplay(): String {
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString())
        }
        return display
    }

    // this checks whether the secret word contains the letter the user has guessed, if so it returns the letter, if not - returns "_"
    private fun checkLetter(str: String) = when (correctGuesses.contains(str)) {
        true -> str
        false -> "_"
    }

    // called each time the user makes a guess
    fun makeGuess(guess: String) {
        if(guess.length == 1) {
            if(secretWord.contains(guess)) {
                correctGuesses += guess
                _secretWordDisplay.value = deriveSecretWordDisplay()
            } else {
                _incorrectGuesses.value += guess
                _livesLeft.value = _livesLeft.value?.minus(1)
            }
            if(isWon() || isLost()) _gameOver.value = true
        }
    }

    // the game is won if the secret word matches secretWordDisplay
    private fun isWon() = secretWord.equals(secretWordDisplay.value, true)

    // the game is lost when the user runs out of lives
    private fun isLost() = (livesLeft.value ?: 0) <= 0

    // returns the string of game result and what the secret word was
    fun wonLostMessage():String {
        var message = ""
        if(isWon()) message = "You won!"
        else if (isLost()) message = "You lost!"
        message += " The word was $secretWord."
        return message
    }

    fun finishGame() {
        _gameOver.value = true
    }
}