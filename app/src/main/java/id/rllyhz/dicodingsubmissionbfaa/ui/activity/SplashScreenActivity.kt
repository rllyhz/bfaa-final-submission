package id.rllyhz.dicodingsubmissionbfaa.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.ui.activity.main.MainActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashScreenActivity : AppCompatActivity() {
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        setupUI()
    }

    private fun setupUI() {
        window.statusBarColor =
            ContextCompat.getColor(applicationContext, R.color.background_color_dark)
        supportActionBar?.hide()

        timer = Timer()

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onResume() {
        super.onResume()

        timer?.schedule(DELAY_TIME_IN_MILLIS) {
            startActivity(
                Intent(this@SplashScreenActivity, MainActivity::class.java)
            )

            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (timer != null)
            timer = null
    }

    companion object {
        private const val DELAY_TIME_IN_MILLIS = 2000L
    }
}