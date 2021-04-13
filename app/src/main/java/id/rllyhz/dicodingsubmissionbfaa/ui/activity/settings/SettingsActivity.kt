package id.rllyhz.dicodingsubmissionbfaa.ui.activity.settings

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.data.pref.ReminderPref
import id.rllyhz.dicodingsubmissionbfaa.databinding.ActivitySettingsBinding
import id.rllyhz.dicodingsubmissionbfaa.ui.feature.picker.TimePickerFragment
import id.rllyhz.dicodingsubmissionbfaa.ui.receiver.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

class SettingsActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var reminderPref: ReminderPref
    private lateinit var nameUserActive: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // this must be from user that being active.
        // but, in this app, it's just dummy data
        nameUserActive = resources.getString(R.string.settings_user_dummy)

        setupActionBar()
        setupUI()
    }

    private fun setupUI() {
        alarmReceiver = AlarmReceiver()
        reminderPref = ReminderPref(this)

        binding.apply {
            tvSettingsLanguageDisplay.text = Locale.getDefault().displayLanguage
            tvSettingsLanguageDisplay.setOnClickListener {
                startActivity(
                    Intent(Settings.ACTION_LOCALE_SETTINGS)
                )
            }


            switchSettingsReminder.isChecked = reminderPref.isAlarmAlreadySet()
            switchSettingsReminder.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    val time = binding.tvSettingsTimeReminder.text.toString()
                    alarmReceiver.setRepeatingAlarm(
                        this@SettingsActivity,
                        time,
                        resources.getString(R.string.settings_reminder_message, nameUserActive)
                    )
                } else {
                    alarmReceiver.setOffRepeatingAlarm(this@SettingsActivity)
                }
            }

            tvSettingsTimeReminder.text = reminderPref.getTimeOfActiveReminder()
            tvSettingsTimeReminder.setOnClickListener {
                changeTimeReminder()
            }

            tvSettingsTimeReminderLabel.setOnClickListener {
                changeTimeReminder()
            }
        }
    }

    private fun changeTimeReminder() {
        TimePickerFragment().show(supportFragmentManager, TIME_PICKER_TAG)
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = getString(R.string.action_bar_title_settings)
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

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        binding.tvSettingsTimeReminder.text = dateFormat.format(calendar.time)
    }


    companion object {
        private const val TIME_PICKER_TAG = "TimePickerOnce"
    }
}