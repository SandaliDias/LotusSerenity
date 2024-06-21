package com.example.mobilegame
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class GameActivity : AppCompatActivity() {
    private lateinit var scoreViewModel: ScoreViewModel
    private lateinit var scoreTextView: TextView
    private lateinit var handler: Handler
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var container: FrameLayout
    var lotusReachedBottom = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_game)

        scoreViewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)
        scoreTextView = findViewById(R.id.scoreTextView)
        container = findViewById(R.id.container)
        sharedPreferences = getSharedPreferences("HighScore", MODE_PRIVATE)

        // Observe changes in score and update the UI
        scoreViewModel.score.observe(this, { score ->
            scoreTextView.text = "Score: $score"
        })

        handler = Handler(Looper.getMainLooper())
        startLotusGenerator()
        updateScore()
    }

    // Generate lotus flowers at random positions
    private fun generateLotus() {
        val lotus = LotusView(this)
        val layoutParams = lotus.layoutParams as FrameLayout.LayoutParams
        val maxLeftMargin = container.width - (100)
        layoutParams.leftMargin = (0..maxLeftMargin).random()
        container.addView(lotus, layoutParams)
        lotus.setOnClickListener {
            container.removeView(lotus)
            scoreViewModel.incrementScore()
        }
        lotus.startFalling(container.height.toFloat())
    }

    // Start generating lotuses periodically
    private fun startLotusGenerator() {
        handler.post(object : Runnable {
            override fun run() {
                generateLotus()
                handler.postDelayed(this, 1000) // Generate lotus every 1000 milliseconds
            }
        })
    }

    // Update score display
    private fun updateScore() {
        scoreTextView.text = "Score: ${scoreViewModel.getScore()}"
    }

    // End the game
    fun endGame() {
        val highScore = sharedPreferences.getInt("highScore", 0)
        if (scoreViewModel.getScore() > highScore) {
            val editor = sharedPreferences.edit()
            editor.putInt("highScore", scoreViewModel.getScore())
            editor.apply()
        }

        if (lotusReachedBottom) {
            // Navigate to GameOverActivity
            val intent = Intent(this, GameOverActivity::class.java)
            intent.putExtra("currentScore", scoreViewModel.getScore())
            intent.putExtra("highScore", sharedPreferences.getInt("highScore", 0))
            startActivity(intent)
        }
    }

    fun incrementScore(){
        return scoreViewModel.incrementScore()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // Remove any pending callbacks to prevent memory leaks
    }
}
