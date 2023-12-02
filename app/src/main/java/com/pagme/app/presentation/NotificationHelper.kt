package com.pagme.app.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.pagme.app.R
import com.pagme.app.presentation.activities.ListDebtActivity

class NotificationHelper(private val context: Context) {

    private val channelId = "reminder_channel"
    private val channelName = "Reminder Channel"
    private val notificationId = 1

    fun createNotification() {
        val intent = Intent(context, ListDebtActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Abra o app")
            .setContentText("Veja se tem alguem que falta pagar!")
            .setSmallIcon(R.drawable.ic_notifications_24)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
