package dev.pegasus.template.helpers

import android.animation.Animator
import android.animation.ObjectAnimator
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import dev.pegasus.template.utils.HelperUtils.TAG

object Animations {
    fun showFrameLayoutWithAnimation(frameLayout: FrameLayout) {
        // Calculate the final Y translation (from bottom to top)
        val translationY = frameLayout.height.toFloat()

        // Create an ObjectAnimator to animate the translationY property
        val animator = ObjectAnimator.ofFloat(frameLayout, View.TRANSLATION_Y, translationY, 0f).apply {
            duration = 3000L  // Animation duration in milliseconds
        }

        // Set a listener to make the FrameLayout invisible after the animation completes
        animator.addListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator) {
                Log.d(TAG, " show -> onAnimationStart: is execute")
            }

            override fun onAnimationEnd(animation: Animator) {
                Log.d(TAG, " show -> onAnimationEnd: is execute")
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })

        // Start the animation
        animator.start()

        // Make the FrameLayout visible
        frameLayout.visibility = View.VISIBLE
    }

    fun hideFrameLayoutWithAnimation(frameLayout: FrameLayout) {
        // Calculate the final Y translation (from top to bottom)
        val translationY = frameLayout.height.toFloat()

        // Create an ObjectAnimator to animate the translationY property
        val animator = ObjectAnimator.ofFloat(frameLayout, View.TRANSLATION_Y, 0f, translationY).apply {
            duration = 3000L  // Animation duration in milliseconds
        }

        // Set a listener to make the FrameLayout invisible after the animation completes
        animator.addListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator) {
                Log.d(TAG, " hide -> onAnimationStart: is execute")
            }

            override fun onAnimationEnd(animation: Animator) {
                Log.d(TAG, " hide -> onAnimationEnd: is execute")
                frameLayout.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })

        // Start the animation
        animator.start()
    }

}