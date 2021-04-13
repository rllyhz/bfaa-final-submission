package id.rllyhz.dicodingsubmissionbfaa.ui.receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import id.rllyhz.dicodingsubmissionbfaa.R
import id.rllyhz.dicodingsubmissionbfaa.ui.activity.main.MainActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        sendAlarmNotification(context, intent)
    }

    private fun sendAlarmNotification(context: Context, intent: Intent) {
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        val mainActivityIntent = Intent(context, MainActivity::class.java)

        val pendingIntent =
            PendingIntent.getActivity(
                context,
                1010,
                mainActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle(CHANNEL_NAME)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, R.color.redish_500))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                enableVibration(true)
                vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            }

            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
    }

    fun setRepeatingAlarm(
        context: Context,
        date: String,
        time: String,
        message: String
    ) {
        if (isDateInvalid(date, DATE_FORMAT) || isDateInvalid(time, TIME_FORMAT)) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }

        val dateArray = date.split("-").toTypedArray()
        val timeArray = time.split(":").toTypedArray()

        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, Integer.parseInt(dateArray[0]))
            set(Calendar.MONTH, Integer.parseInt(dateArray[1]) - 1)
            set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[2]))
            set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
            set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
            set(Calendar.SECOND, 0)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, REPEAT_CODE, intent, 0)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        showFeedback(context, "Alarm set up!")
    }

    fun setOffRepeatingAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, REPEAT_CODE, intent, 0)

        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)

        showFeedback(context, "Success")
    }

    fun showFeedback(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun isDateInvalid(date: String, format: String): Boolean {
        return try {
            val df = SimpleDateFormat(format, Locale.getDefault())
            df.isLenient = false
            df.parse(date)
            false
        } catch (e: ParseException) {
            true
        }
    }

    companion object {
        private const val TIME_FORMAT = "HH:mm"
        private const val DATE_FORMAT = "yyyy-MM-dd"
        private const val NOTIFICATION_ID = 1212

        const val EXTRA_MESSAGE = "EXTRA_MESSAGE"

        private const val CHANNEL_ID = "id.rllyhz.dicodingsubmissionbfaa.ui.receiver.alarmReceiver"
        private const val CHANNEL_NAME = "Reminder Github"
        private const val REPEAT_CODE = 101
    }
}