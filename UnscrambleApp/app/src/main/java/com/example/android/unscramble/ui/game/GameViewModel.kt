package com.example.android.unscramble.ui.game


import android.text.Spannable
import android.text.SpannableString
import android.text.style.TtsSpan
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val _score = MutableLiveData(0)
        val score: MutableLiveData<Int>
        get()= _score

    private var _currentWordCount = MutableLiveData(0)
        val currentWordCount: LiveData<Int>
        get() = _currentWordCount



    private lateinit var _currentScrambledWord:MutableLiveData<String>
    val currentScrambleWord: LiveData<Spannable> = Transformations.map(_currentScrambledWord){
        if(it == null){
            SpannableString("")
        }else{
            val scrambleWord = it.toString()
            val spannable: Spannable = SpannableString(scrambleWord)
            spannable.setSpan(
                TtsSpan.VerbatimBuilder(scrambleWord).build(), 0,
                scrambleWord.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            spannable
        }
    }



    private lateinit var currentWord: String
    private var wordsList: MutableList<String> = mutableListOf()

    init{
        getNextWord()
    }


    private fun getNextWord(){
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while (tempWord.toString().equals(currentWord, false)) {
            tempWord.shuffle()
        }
        if(wordsList.contains(currentWord)){
            getNextWord()
        }else {
            _currentScrambledWord.value = String(tempWord)
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordsList.add(currentWord)
        }
    }

    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }


    fun nextWord(): Boolean{
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS){
            getNextWord()
            true
        }else false

    }

    private fun increaseScore(){
        _score.value =(_score.value)?.plus(SCORE_INCREASE)
    }

    fun isUserWordCorrect(playerWord: String):Boolean{
        if(playerWord.equals(currentWord, true)){
            increaseScore()
            return true
        }
        return false
    }
}