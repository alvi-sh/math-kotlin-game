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

class SubtractionActivity : AppCompatActivity() {

    private lateinit var scoreTextView1: TextView
    private lateinit var lifeTextView1: TextView
    private lateinit var timeTextView1: TextView
    private lateinit var questionTextView1: TextView
    private lateinit var randomTextView1: TextView
    private lateinit var answerText1: EditText
    private lateinit var checkButton1: Button
    private lateinit var nextButton1: Button

    private var score = 0
    private var life = 5
    private var currentAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subtraction)

        // Initialize views
        scoreTextView1 = findViewById(R.id.scoreTextView1)
        lifeTextView1 = findViewById(R.id.lifeTextView1)
        timeTextView1 = findViewById(R.id.timeTextView1)
        questionTextView1 = findViewById(R.id.questionTextView1)
        randomTextView1 = findViewById(R.id.randomTextView1)
        answerText1 = findViewById(R.id.answerText1)
        checkButton1 = findViewById(R.id.checkButton1)
        nextButton1 = findViewById(R.id.nextButton1)

        scoreTextView1.text = "Score: $score"
        lifeTextView1.text = "Life: $life"
        startNewGame()

        val timer = object : CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeTextView1.text = "00:${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                timeTextView1.text = "00:00"
                Toast.makeText(this@SubtractionActivity, "Time's up!", Toast.LENGTH_SHORT).show()
                checkButton1.isEnabled = false
                nextButton1.isEnabled = true
            }
        }

        timer.start()

        checkButton1.setOnClickListener {
            val userAnswer = answerText1.text.toString().toIntOrNull()
            if (userAnswer != null && userAnswer == currentAnswer) {
                score++
                scoreTextView1.text = "Score: $score"
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
                nextButton1.isEnabled = true
            } else {
                life--
                lifeTextView1.text = "Life: $life"
                Toast.makeText(this, "Wrong answer!", Toast.LENGTH_SHORT).show()
                if (life == 0) {
                    Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show()
                    checkButton1.isEnabled = false
                    nextButton1.isEnabled = false
                }
            }
        }

        nextButton1.setOnClickListener {
            if (life > 0) {
                startNewGame()
                timer.start()
                nextButton1.isEnabled = false
                checkButton1.isEnabled = true
                answerText1.text.clear()
            } else {
                val intent = Intent(this, MultiplicationActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun startNewGame() {
        val num1 = Random.nextInt(0, 100)
        val num2 = Random.nextInt(0, num1)
        currentAnswer = num1 - num2
        randomTextView1.text = "$num1 - $num2"
    }
}
