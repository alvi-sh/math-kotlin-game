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

class MultiplicationActivity : AppCompatActivity() {

    private lateinit var scoreTextView2: TextView
    private lateinit var lifeTextView2: TextView
    private lateinit var timeTextView2: TextView
    private lateinit var questionTextView2: TextView
    private lateinit var randomTextView2: TextView
    private lateinit var answerText2: EditText
    private lateinit var checkButton2: Button
    private lateinit var nextButton2: Button

    private var score = 0
    private var life = 5
    private var currentAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiplication)

        scoreTextView2 = findViewById(R.id.scoreTextView2)
        lifeTextView2 = findViewById(R.id.lifeTextView2)
        timeTextView2 = findViewById(R.id.timeTextView2)
        questionTextView2 = findViewById(R.id.questionTextView2)
        randomTextView2 = findViewById(R.id.randomTextView2)
        answerText2 = findViewById(R.id.answerText2)
        checkButton2 = findViewById(R.id.checkButton2)
        nextButton2 = findViewById(R.id.nextButton2)

        scoreTextView2.text = "Score: $score"
        lifeTextView2.text = "Life: $life"
        startNewGame()

        val timer = object : CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeTextView2.text = "00:${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                timeTextView2.text = "00:00"
                Toast.makeText(this@MultiplicationActivity, "Time's up!", Toast.LENGTH_SHORT).show()
                checkButton2.isEnabled = false
                nextButton2.isEnabled = true
            }
        }

        timer.start()

        checkButton2.setOnClickListener {
            val userAnswer = answerText2.text.toString().toIntOrNull()
            if (userAnswer != null && userAnswer == currentAnswer) {
                score++
                scoreTextView2.text = "Score: $score"
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
                nextButton2.isEnabled = true
            } else {
                life--
                lifeTextView2.text = "Life: $life"
                Toast.makeText(this, "Wrong answer!", Toast.LENGTH_SHORT).show()
                if (life == 0) {
                    Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show()
                    checkButton2.isEnabled = false
                    nextButton2.isEnabled = false
                }
            }
        }

        nextButton2.setOnClickListener {
            if (life > 0) {
                startNewGame()
                timer.start()
                nextButton2.isEnabled = false
                checkButton2.isEnabled = true
                answerText2.text.clear()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun startNewGame() {
        val num1 = Random.nextInt(0, 10)
        val num2 = Random.nextInt(0, 10)
        currentAnswer = num1 * num2
        randomTextView2.text = "$num1 * $num2"
    }
}
