package com.example.myfirstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val result = intent.getStringExtra("RESULT")
        val word = intent.getStringExtra("WORD") ?: ""

        val resultTextView = findViewById<TextView>(R.id.resultTextView)
        val wordTextView = findViewById<TextView>(R.id.wordTextView)
        val playAgainButton = findViewById<Button>(R.id.playAgainButton)

        if (result == "win") {
            resultTextView.text = "🎉 ПОБЕДА! 🎉"
            resultTextView.setTextColor(getColor(android.R.color.holo_green_dark))
        } else {
            resultTextView.text = "💀 ПОРАЖЕНИЕ 💀"
            resultTextView.setTextColor(getColor(android.R.color.holo_red_dark))
        }

        wordTextView.text = "Слово было: $word"

        playAgainButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}