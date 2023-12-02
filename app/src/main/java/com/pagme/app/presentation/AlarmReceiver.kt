package com.pagme.app.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationHelper = NotificationHelper(context)
        notificationHelper.createNotification()
    }
}
