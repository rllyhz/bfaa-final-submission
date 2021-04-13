package id.rllyhz.dicodingsubmissionbfaa.ui.activity.alarm

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = getString(R.string.action_bar_title_alarm_setting)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_left)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}