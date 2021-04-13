package id.rllyhz.dicodingsubmissionbfaa.data.pref

import android.content.Context

class ReminderPref(context: Context) {

    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun isAlarmAlreadySet(): Boolean =
        pref.getBoolean(EXTRA_REMINDER_SET, false)

    fun updateAlarmSetup(state: Boolean) {
        pref.edit().apply {
            putBoolean(EXTRA_REMINDER_SET, state)
            apply()
        }
    }


    companion object {
        const val PREF_NAME = "id.rllyhz.dicodingsubmissionbfaa.prefs"
        private const val EXTRA_REMINDER_SET = "EXTRA_REMINDER_SET"
    }
}