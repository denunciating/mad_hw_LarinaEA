package com.example.myfirstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<Button>(R.id.startGameButton)
        startButton.setOnClickListener {
            // Переходим к экрану ввода слова
            val intent = Intent(this, InputWordActivity::class.java)
            startActivity(intent)
        }
    }
}