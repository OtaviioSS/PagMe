package com.pagme.app.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.pagme.app.R
import com.pagme.app.presentation.activities.ListDebtActivity

class BackgroundService : Service() {


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 1. Criar um canal de notificação (válido para Android 8.0 e versões posteriores)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "channel_id"
            val channelName = "Channel Name"
            val channelDescription = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }

            // Registrar o canal de notificação no NotificationManager
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

// 2. Criar o conteúdo da notificação
        val notificationTitle = "Título da Notificação"
        val notificationText = "Texto da Notificação"
        val notificationIntent = Intent(this, ListDebtActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationBuilder = NotificationCompat.Builder(this, "channel_id")
            .setSmallIcon(R.drawable.ic_notifications_24)
            .setContentTitle(notificationTitle)
            .setContentText(notificationText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // Fechar a notificação ao ser clicada

// 3. Exibir a notificação
        val notificationId = 1
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notificationBuilder.build())

        // Retorne START_STICKY para reiniciar o serviço automaticamente se for interrompido pelo sistema
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun exibirNotificacao() {
        // Lógica para criar e exibir a notificação
        // ... (mesmo código de criação de notificação mencionado anteriormente)
    }

    companion object {
        // ID do canal de notificação
        private const val CHANNEL_ID = "channel_id"
    }
}