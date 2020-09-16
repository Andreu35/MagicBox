package com.monstarlab.magicbox.ui.features.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.monstarlab.magicbox.R
import com.monstarlab.magicbox.ui.MainActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        activityScope.launch {
            delay(3000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in)
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }

}