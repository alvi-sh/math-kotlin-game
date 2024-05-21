package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class AdditionActivity : AppCompatActivity() {

    private lateinit var scoreTextView: TextView
    private lateinit var lifeTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var questionTextView: TextView
    private lateinit var randomTextView: TextView
    private lateinit var answerText: EditText
    private lateinit var checkButton: Button
    private lateinit var nextButton: Button

    private var score = 0
    private var life = 5
    private var currentAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addition)

        scoreTextView = findViewById(R.id.scoreTextView)
        lifeTextView = findViewById(R.id.lifeTextView)
        timeTextView = findViewById(R.id.timeTextView)
        questionTextView = findViewById(R.id.questionTextView)
        randomTextView = findViewById(R.id.randomTextView)
        answerText = findViewById(R.id.answerText)
        checkButton = findViewById(R.id.checkButton)
        nextButton = findViewById(R.id.nextButton)

        scoreTextView.text = "Score: $score"
        lifeTextView.text = "Life: $life"
        startNewGame()

        val timer = object : CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeTextView.text = "00:${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                timeTextView.text = "00:00"
                Toast.makeText(this@AdditionActivity, "Time's up!", Toast.LENGTH_SHORT).show()
                checkButton.isEnabled = false
                nextButton.isEnabled = true
            }
        }

        timer.start()

        checkButton.setOnClickListener {
            val userAnswer = answerText.text.toString().toIntOrNull()
            if (userAnswer != null && userAnswer == currentAnswer) {
                score++
                scoreTextView.text = "Score: $score"
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
                nextButton.isEnabled = true
            } else {
                life--
                lifeTextView.text = "Life: $life"
                Toast.makeText(this, "Wrong answer!", Toast.LENGTH_SHORT).show()
                if (life == 0) {
                    Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show()
                    checkButton.isEnabled = false
                    nextButton.isEnabled = false
                }
            }
        }

        nextButton.setOnClickListener {
            if (life > 0) {
                startNewGame()
                timer.start()
                nextButton.isEnabled = false
                checkButton.isEnabled = true
                answerText.text.clear()
            } else {
                val intent = Intent(this, SubtractionActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun startNewGame() {
        val num1 = Random.nextInt(0, 100)
        val num2 = Random.nextInt(0, 100)
        currentAnswer = num1 + num2
        randomTextView.text = "$num1 + $num2"
    }
}
