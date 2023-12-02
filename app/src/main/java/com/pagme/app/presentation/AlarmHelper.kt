package com.pagme.app.presentation

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

class AlarmHelper(private val context: Context) {

    fun scheduleAlarms() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )

        // Obter a hora e minuto desejados
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR, 10) // Define a hora para 9 (9 PM)
        calendar.set(Calendar.MINUTE, 27) // Define os minutos para 30
        calendar.set(Calendar.AM_PM, Calendar.PM)  // Definir a hora desejada (por exemplo, 9h)

        val interval = 60 * 1000 // Intervalo de 1 minuto (60 segundos * 1000 milissegundos)

        // Configurar o alarme para disparar diariamente no horário definido
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(), interval.toLong(),
            pendingIntent
        )    }
}
