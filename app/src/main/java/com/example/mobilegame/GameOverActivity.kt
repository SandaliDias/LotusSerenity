package com.example.mobilegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class GameOverActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            supportActionBar?.hide()
            setContentView(R.layout.activity_game_over)

            val currentScore = intent.getIntExtra("currentScore", 0)
            val highScore = intent.getIntExtra("highScore", 0)

            findViewById<TextView>(R.id.currentScoreTextView).text = "Current Score: $currentScore"
            findViewById<TextView>(R.id.highScoreTextView).text = "High Score: $highScore"

            val playButton = findViewById<Button>(R.id.play_button)
            playButton.setOnClickListener {
                val intent = Intent(this, GameActivity::class.java)
                startActivity(intent)
            }

            val homeButton = findViewById<Button>(R.id.home_button)
            homeButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
}