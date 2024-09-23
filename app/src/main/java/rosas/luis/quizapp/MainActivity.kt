package rosas.luis.quizapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
//import android.renderscript.ScriptGroup.Binding
//import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
//import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import rosas.luis.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private val chetLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        result -> //Handle the result
    }

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Handle the result
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }

    }

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

            binding.trueButton.setOnClickListener {
                checkAnswer(true)
            }
            binding.falseButton.setOnClickListener {
                checkAnswer(false)
            }

            binding.nextButton.setOnClickListener {
                quizViewModel.moveToNext()
                updateQuestion()
            }

            binding.cheatButton.setOnClickListener {
                // Start CheatActivity
                val answerIsTrue = quizViewModel.currentQuestionAnswer
                val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
                //val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
                cheatLauncher.launch(intent)
            }

            binding.prevButton.setOnClickListener {
                val correctAnswer = quizViewModel.currentQuestionAnswer
                updateQuestion()
            }
            updateQuestion()
    }

            private fun updateQuestion(){
                val questionTextResId = quizViewModel.currentQuestionText
                binding.textQuestion.setText(questionTextResId)
            }
            private fun checkAnswer(userAnswer:Boolean){
                val correctAnswer = quizViewModel.currentQuestionAnswer
                val messageResId = when {
                    quizViewModel.isCheater -> R.string.judgment_toast
                    userAnswer == correctAnswer -> R.string.correct_toast
                    else -> R.string.incorrect_toast
                }
                Toast.makeText(this, messageResId, Toast.LENGTH_LONG).show()
            }
}