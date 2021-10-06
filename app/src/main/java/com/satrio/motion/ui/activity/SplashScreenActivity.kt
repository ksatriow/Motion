package com.satrio.motion.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.satrio.motion.R
import com.satrio.motion.ui.activity.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        GlobalScope.launch(Dispatchers.IO) {
            delay(2500)
            MainActivity.start(this@SplashScreenActivity)
            finish()
        }

    }
}