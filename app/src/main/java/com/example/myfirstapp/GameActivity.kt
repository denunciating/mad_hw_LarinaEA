package com.example.myfirstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class GameActivity : AppCompatActivity() {
    private lateinit var secretWord: String
    private lateinit var displayedWord: CharArray
    private val wrongLetters = mutableListOf<Char>()
    private var attemptsLeft = 7

    private lateinit var wordTextView: TextView
    private lateinit var wrongLettersTextView: TextView
    private lateinit var attemptsTextView: TextView
    private lateinit var hangmanTextView: TextView
    private lateinit var letterEditText: EditText
    private lateinit var guessButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Получаем загаданное слово
        secretWord = intent.getStringExtra("SECRET_WORD") ?: "КОТ"
        displayedWord = CharArray(secretWord.length) { '_' }

        // Находим все View
        wordTextView = findViewById(R.id.wordTextView)
        wrongLettersTextView = findViewById(R.id.wrongLettersTextView)
        attemptsTextView = findViewById(R.id.attemptsTextView)
        hangmanTextView = findViewById(R.id.hangmanTextView)
        letterEditText = findViewById(R.id.letterEditText)
        guessButton = findViewById(R.id.guessButton)

        updateDisplay()

        guessButton.setOnClickListener {
            val letter = letterEditText.text.toString().uppercase().getOrNull(0)

            if (letter == null || !letter.isLetter() || letter !in 'А'..'Я') {
                Toast.makeText(this, "Введите одну русскую букву!", Toast.LENGTH_SHORT).show()
            } else {
                checkLetter(letter)
                letterEditText.text.clear()
            }
        }
    }

    private fun checkLetter(letter: Char) {
        if (letter in secretWord) {
            // Буква есть в слове
            for (i in secretWord.indices) {
                if (secretWord[i] == letter) {
                    displayedWord[i] = letter
                }
            }
            Toast.makeText(this, "Есть такая буква!", Toast.LENGTH_SHORT).show()
        } else {
            // Буквы нет
            if (letter !in wrongLetters) {
                wrongLetters.add(letter)
                attemptsLeft--
            }
            Toast.makeText(this, "Нет такой буквы!", Toast.LENGTH_SHORT).show()
        }

        updateDisplay()
        checkGameStatus()
    }

    private fun updateDisplay() {
        // Обновляем отображение слова
        wordTextView.text = displayedWord.joinToString("")

        // Обновляем неправильные буквы
        wrongLettersTextView.text = "Ошибки: ${wrongLetters.joinToString(", ")}"

        // Обновляем количество попыток
        attemptsTextView.text = "Осталось попыток: $attemptsLeft"

        // Обновляем виселицу (простая текстовая версия)
        val hangmanStages = listOf(
            "",  // 0 ошибок (полный человечек)
            "  O",         // 2 ошибки
            "  O\n /",           // 3 ошибки
            "  O\n /|",            // 4 ошибки
            "  O\n /|\\",                // 5 ошибок
            "  O\n /|\\\n /",                 // 6 ошибок (пусто)
            "  O\n /|\\\n / \\",                    // 7 ошибок (игра окончена)
            "  O\n /|\\\n / \\"
        )

        val stageIndex = (7 - attemptsLeft).coerceAtMost(7)
        hangmanTextView.text = hangmanStages[stageIndex]
    }

    private fun checkGameStatus() {
        // Проверяем победу
        if (!displayedWord.contains('_')) {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("RESULT", "win")
            intent.putExtra("WORD", secretWord)
            startActivity(intent)
            finish()
        }
        // Проверяем поражение
        else if (attemptsLeft <= 0) {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("RESULT", "lose")
            intent.putExtra("WORD", secretWord)
            startActivity(intent)
            finish()
        }
    }
}