package id.rllyhz.dicodingsubmissionbfaa.data.pref

import android.content.Context

class ReminderPref(context: Context) {

    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun isAlarmAlreadySet(): Boolean =
        pref.getBoolean(EXTRA_REMINDER_SET, false)

    fun updateAlarmSetup(state: Boolean, time: String?) {
        pref.edit().apply {
            putBoolean(EXTRA_REMINDER_SET, state)

            if (state) {
                putString(EXTRA_REMINDER_TIME, time ?: "")
            } else {
                remove(EXTRA_REMINDER_TIME)
            }

            apply()
        }
    }

    fun getTimeOfActiveReminder(): String? =
        pref.getString(EXTRA_REMINDER_TIME, PREF_TIME_REMINDER_DEFAULT)


    companion object {
        const val PREF_NAME = "id.rllyhz.dicodingsubmissionbfaa.prefs"
        const val PREF_TIME_REMINDER_DEFAULT = "09:00"
        private const val EXTRA_REMINDER_SET = "EXTRA_REMINDER_SET"
        private const val EXTRA_REMINDER_TIME = "EXTRA_REMINDER_TIME"
    }
}