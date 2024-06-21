package com.example.mobilegame

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView

class LotusView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var isTapped = false // Flag to track if the lotus has been tapped

    init {
        // Set your lotus drawable as the image resource
        setImageResource(R.drawable.lotus)

        // Specify your desired size in pixels
        val sizeInPixels = context.resources.getDimensionPixelSize(R.dimen.lotus_size)

        // Set layout parameters to make lotus view square with fixed size
        val layoutParams = FrameLayout.LayoutParams(sizeInPixels, sizeInPixels)
        this.layoutParams = layoutParams
    }

    fun startFalling(containerHeight: Float) {
        // Start falling animation
        animate().translationY(containerHeight - height)
            .setDuration(5000)
            .withEndAction {
                // Remove the lotus when it reaches the bottom
                if (translationY >= containerHeight - height && !isTapped) {
                    (context as? GameActivity)?.lotusReachedBottom = true
                    (context as? GameActivity)?.endGame()
                } else {
                    // Remove the lotus if it has been tapped or if it reaches the bottom
                    (parent as? FrameLayout)?.removeView(this)
                }
            }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            isTapped = true // Set isTapped to true when the lotus is tapped
            // Remove the lotus when tapped
            (parent as? FrameLayout)?.removeView(this)
            // Increment score when tapped
            (context as? GameActivity)?.incrementScore()
            return true
        }
        return super.onTouchEvent(event)
    }
}
