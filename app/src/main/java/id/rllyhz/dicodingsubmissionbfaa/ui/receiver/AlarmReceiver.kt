package id.rllyhz.dicodingsubmissionbfaa.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        sendNotification(context, intent)
    }

    private fun sendNotification(context: Context?, intent: Intent?) {
        creatingNotification(context, intent)
    }

    private fun creatingNotification(context: Context?, intent: Intent?) {
        val type = intent?.getStringExtra(EXTRA_TYPE)
        val message = intent?.getStringExtra(EXTRA_MESSAGE)

        val title =
            if (type.equals(TYPE_ONE_TIME, ignoreCase = true)) TYPE_ONE_TIME else TYPE_REPEATING
        val notificationID =
            if (type.equals(TYPE_ONE_TIME, ignoreCase = true)) ONETIME_CODE else REPEAT_CODE
    }

    fun setOneTimeAlarm(
        context: Context,
        type: String,
        date: String,
        time: String,
        message: String
    ) {
        //
    }

    fun setRepeatingAlarm() {
        //
    }

    companion object {
        const val TYPE_ONE_TIME = "OneTimeAlarm"
        const val TYPE_REPEATING = "RepeatingAlarm"

        private const val TIME_FORMAT = "HH:mm"
        private const val DATE_FORMAT = "yyyy-MM-dd"
        private const val NOTIFICATION_ID = 1212

        const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
        const val EXTRA_TYPE = "EXTRA_TYPE"

        private const val CHANNEL_ID = "id.rllyhz.dicodingsubmissionbfaa.ui.receiver.alarmReceiver"
        private const val CHANNEL_NAME = "id.rllyhz.dicodingsubmissionbfaa.ui.receiver.Reminder"
        private const val REPEAT_CODE = 101
        private const val ONETIME_CODE = 102
    }
}