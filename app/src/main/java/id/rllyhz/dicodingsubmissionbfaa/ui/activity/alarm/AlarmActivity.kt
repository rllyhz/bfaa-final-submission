package id.rllyhz.dicodingsubmissionbfaa.ui.activity.alarm

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.databinding.ActivityAlarmBinding
import id.rllyhz.dicodingsubmissionbfaa.ui.feature.picker.DatePickerFragment
import id.rllyhz.dicodingsubmissionbfaa.ui.feature.picker.TimePickerFragment
import id.rllyhz.dicodingsubmissionbfaa.ui.receiver.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

class AlarmActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener,
    TimePickerFragment.DialogTimeListener {
    private lateinit var binding: ActivityAlarmBinding
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            icAlarmDate.setOnClickListener {
                DatePickerFragment().apply {
                    show(supportFragmentManager, DATE_PICKER_TAG)
                }
            }

            icAlarmTime.setOnClickListener {
                TimePickerFragment().apply {
                    show(supportFragmentManager, TIME_PICKER_TAG)
                }
            }

            btnAlarmSetUp.setOnClickListener {
                //
            }
        }
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

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, date: Int) {
        val calendar = Calendar.getInstance().apply {
            set(year, month, date)
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        binding.tvAlarmDate.text = dateFormat.format(calendar.time)
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            TIME_PICKER_TAG -> binding.tvAlarmTime.text = dateFormat.format(calendar.time)
        }
    }

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_TAG = "TimePickerOnce"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }
}