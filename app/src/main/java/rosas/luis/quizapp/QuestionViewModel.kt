package rosas.luis.quizapp

//import android.util.Log
import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

const val IS_CHEATER_KEY = "IS_CHEATER_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private val bancoPreguntas = listOf(
        Question(R.string.pregunta_materia, respuesta = true),
        Question(R.string.pregunta_continuar, respuesta = false)
    )

    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    private var currentIndex : Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = bancoPreguntas[currentIndex].respuesta
    val currentQuestionText: Int
        get() = bancoPreguntas[currentIndex].textoPregunta
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % bancoPreguntas.size
    }

}