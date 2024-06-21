package com.example.mobilegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        var sharedPreferences = getSharedPreferences("HighScore", MODE_PRIVATE)
        val highScore = sharedPreferences.getInt("highScore", 0)

        findViewById<TextView>(R.id.highScoreTextView).text = "High Score: $highScore"

        val playButton = findViewById<Button>(R.id.play_button)
        playButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

    }

    // Button click event to start the gameplay

}