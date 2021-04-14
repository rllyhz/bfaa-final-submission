package id.rllyhz.dicodingsubmissionbfaa.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.ui.activity.main.MainActivity
import java.util.*

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        setupUI()
    }

    private fun setupUI() {
        window.statusBarColor =
            ContextCompat.getColor(applicationContext, R.color.background_color_dark)
        supportActionBar?.hide()

        runnable = Runnable {
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()

            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        handler = Handler(mainLooper).apply {
            postDelayed(runnable, DELAY_TIME_IN_MILLIS)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        handler.removeCallbacks(runnable)
    }

    companion object {
        private const val DELAY_TIME_IN_MILLIS = 2000L
    }
}