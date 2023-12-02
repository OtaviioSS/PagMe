package com.pagme.app.presentation.adapter

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.pagme.app.R
import com.pagme.app.data.model.Payment
import com.pagme.app.databinding.ItemPaymentBinding
import com.pagme.app.presentation.activities.DetailDebtActivity
import com.pagme.app.presentation.service.NotificationService
import com.pagme.app.util.STATUS_ATRASADO
import com.pagme.app.util.STATUS_PAGO
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import com.pagme.app.util.extensions.formataParaMoedaBrasileira


class PaymentAdapter(
    private val onItemClick: (Payment) -> Unit,
    private var paymentList: MutableList<Payment>
) :
    RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        return PaymentAdapter.PaymentViewHolder(
            ItemPaymentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),parent.context
        )
    }

    override fun getItemCount(): Int {
        return paymentList.size
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val payment = paymentList[position]
        holder.bind(payment, onItemClick)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPayments(payments: MutableList<Payment>) {

        this.paymentList = payments
        notifyDataSetChanged()
    }

    class PaymentViewHolder(private val binding: ItemPaymentBinding,private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(payment: Payment, onItemClick: (Payment) -> Unit) {
            val context = binding.root.context
            val CHANNEL_ID = "payment_channel"
            val NOTIFICATION_ID = 1


            try {
                val currentDate = Timestamp.now()
                binding.statusPaymentItemView.text = payment.paymentStatus
                binding.valuePaymentItemView.text =
                    payment.paymentValue.toBigDecimal().formataParaMoedaBrasileira()
                binding.dueDatePaymentItemView.text = SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
                ).format(payment.paymentDueDate)
                if (binding.statusPaymentItemView.text == STATUS_PAGO) {
                    binding.buttonNotPayPaymentItemView.visibility = View.VISIBLE
                    binding.buttonPayPaymentItemView.visibility = View.GONE
                    val layoutParams =
                        binding.statusPaymentItemView.layoutParams as ConstraintLayout.LayoutParams
                    layoutParams.endToStart = R.id.buttonNotPayPaymentItemView
                    binding.statusPaymentItemView.layoutParams = layoutParams
                }
                if (currentDate > Timestamp(payment.paymentDueDate)) {
                    binding.statusPaymentItemView.text = STATUS_ATRASADO
                    binding.root.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.md_theme_dark_errorContainer
                        )
                    )
                    /*   val intent = Intent(binding.root.context, NotificationService::class.java)
                       intent.putExtra("payment", payment)
                       binding.root.context.startService(intent)
   */

                    lateinit var notificationManager: NotificationManager
                    var pendingIntent: PendingIntent? = null
                    lateinit var notificationBuilder: NotificationCompat.Builder
                    val channelId = "payment_channel"
                    val channelName = "Payment Notifications"
                    val channelDescription = "Notifications for overdue payments"
                    notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.createNotificationChannel(
                        NotificationChannel(
                            channelId,
                            channelName,
                            NotificationManager.IMPORTANCE_DEFAULT
                        ).apply {
                            description = channelDescription
                        })
                    pendingIntent = PendingIntent.getActivity(
                        binding.root.context,
                        0,
                        Intent(binding.root.context, DetailDebtActivity::class.java),
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                    notificationBuilder = NotificationCompat.Builder(binding.root.context, channelId)
                        .setSmallIcon(R.drawable.ic_notifications_24)
                        .setContentTitle(STATUS_ATRASADO)
                        .setContentText("Uma parcela no valor de R$${payment.paymentValue} esta atrasado!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                    val notificationId = 5
                    notificationManager.notify(notificationId, notificationBuilder.build())

                }
                binding.buttonPayPaymentItemView.setOnClickListener {
                    onItemClick(payment)
                    binding.buttonNotPayPaymentItemView.visibility = View.VISIBLE
                    binding.buttonPayPaymentItemView.visibility = View.GONE
                    val layoutParams =
                        binding.statusPaymentItemView.layoutParams as ConstraintLayout.LayoutParams
                    layoutParams.endToStart = R.id.coordinatorLayoutFAB
                    binding.statusPaymentItemView.layoutParams = layoutParams

                }
                binding.buttonNotPayPaymentItemView.setOnClickListener {
                    onItemClick(payment)
                    binding.buttonNotPayPaymentItemView.visibility = View.GONE
                    binding.buttonPayPaymentItemView.visibility = View.VISIBLE
                    val layoutParams =
                        binding.statusPaymentItemView.layoutParams as ConstraintLayout.LayoutParams
                    layoutParams.endToStart = R.id.buttonPayPaymentItemView
                    binding.statusPaymentItemView.layoutParams = layoutParams
                }

            } catch (e: ParseException) {
                Log.e("PaymentAdapter", "Error parsing date: ${e.message}")
                e.printStackTrace()
            }
        }

    }


}