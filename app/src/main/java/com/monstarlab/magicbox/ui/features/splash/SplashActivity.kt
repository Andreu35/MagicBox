package com.monstarlab.magicbox.ui.features.splash

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.monstarlab.magicbox.R
import com.monstarlab.magicbox.databinding.ActivitySplashBinding
import com.monstarlab.magicbox.extensions.goneUnless
import com.monstarlab.magicbox.ui.MainActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    private val activityScope = CoroutineScope(Dispatchers.IO)
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val translate = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_bottom)
        translate.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) { binding.text1.goneUnless(true) }
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                val translate2 = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.translate_left_to_right)
                translate2.setAnimationListener(object: Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) { binding.text2.goneUnless(true) }
                    override fun onAnimationRepeat(animation: Animation?) {}
                    override fun onAnimationEnd(animation: Animation?) {
                        activityScope.launch {
                            delay(2000)
                            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                            finish()
                            overridePendingTransition(R.anim.fade_out, R.anim.fade_in)
                        }
                    }
                })
                binding.text2.startAnimation(translate2)
            }
        })

        binding.text1.startAnimation(translate)
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }

}