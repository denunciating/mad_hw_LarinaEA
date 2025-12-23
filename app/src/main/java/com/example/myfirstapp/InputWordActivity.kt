package com.example.myfirstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class InputWordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_word)

        val wordEditText = findViewById<EditText>(R.id.wordEditText)
        val startButton = findViewById<Button>(R.id.startGameButton)

        startButton.setOnClickListener {
            val word = wordEditText.text.toString().trim().uppercase()

            if (word.isEmpty()) {
                Toast.makeText(this, "Введите слово!", Toast.LENGTH_SHORT).show()
            } else if (!word.all { it in 'А'..'Я' || it == 'Ё' }) {
                Toast.makeText(this, "Только русские буквы!", Toast.LENGTH_SHORT).show()
            } else {
                // Передаем слово в GameActivity
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("SECRET_WORD", word)
                startActivity(intent)
                finish() // Закрываем этот экран
            }
        }
    }
}