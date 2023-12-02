package com.pagme.app.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.pagme.app.R
import com.pagme.app.data.model.Payment
import com.pagme.app.domain.usecase.PaymentUseCase
import com.pagme.app.presentation.activities.DetailDebtActivity
import com.pagme.app.util.STATUS_ATRASADO
import java.util.UUID

class NotificationService() : Service() {


    private lateinit var notificationManager: NotificationManager
    private var pendingIntent: PendingIntent? = null
    private lateinit var notificationBuilder: NotificationCompat.Builder
    val channelId = "payment_channel"
    val channelName = "Payment Notifications"
    val channelDescription = "Notifications for overdue payments"

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(
            NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = channelDescription
            })
        pendingIntent = PendingIntent.getActivity(this, 0, Intent(this, DetailDebtActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notifications_24)
            .setContentTitle(STATUS_ATRASADO)
            .setContentText("O pagamento TAL esta atrasada!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        val notificationId = 5
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, notificationBuilder.build())
        Log.i("AQUI", "aqui")
        return Service.START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        // Não é necessário implementar este método
        return null
    }

 /*   suspend fun checkPayments(debtID: String) {
        val payments = paymentUseCase.getAll(debtID)
        for (payment in payments) {
            if (payment.paymentStatus == STATUS_ATRASADO) {
                Log.e("Payments", "Payment is overdue")
            }
        }
    }*/

    private fun sendNotification() {
        // Crie uma notificação
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notifications_24)
            .setContentTitle(STATUS_ATRASADO)
            .setContentText("O pagamento TAL esta atrasada!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Envie a notificação
        val notificationId = 1
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

}
