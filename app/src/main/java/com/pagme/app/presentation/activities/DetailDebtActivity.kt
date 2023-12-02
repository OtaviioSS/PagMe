package com.pagme.app.presentation.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pagme.app.MyApplication
import com.pagme.app.R
import com.pagme.app.data.model.Debt
import com.pagme.app.databinding.ActivityDetailDebtBinding
import com.pagme.app.presentation.adapter.PaymentAdapter
import com.pagme.app.presentation.service.BackgroundService
import com.pagme.app.presentation.service.NotificationService
import com.pagme.app.presentation.viewmodel.CardViewModel
import com.pagme.app.presentation.viewmodel.CardViewModelFactory
import com.pagme.app.presentation.viewmodel.DebtViewModel
import com.pagme.app.presentation.viewmodel.DebtViewModelFactory
import com.pagme.app.presentation.viewmodel.PaymentViewModel
import com.pagme.app.presentation.viewmodel.PaymentViewModelFactory
import com.pagme.app.util.DEBT_KEY_ID
import com.pagme.app.util.STATUS_CRIADO
import com.pagme.app.util.STATUS_PAGO
import com.pagme.app.util.extensions.formataParaMoedaBrasileira
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailDebtActivity : AppCompatActivity() {

    @Inject
    lateinit var debtViewModelFactory: DebtViewModelFactory

    @Inject
    lateinit var cardViewModelFactory: CardViewModelFactory

    @Inject
    lateinit var paymentViewModelFactory: PaymentViewModelFactory

    private lateinit var debtViewModel: DebtViewModel
    private lateinit var cardViewModel: CardViewModel
    private lateinit var paymentViewModel: PaymentViewModel
    private lateinit var paymentAdapter: PaymentAdapter
    private lateinit var notificationService: NotificationService


    private lateinit var currentDebt: Debt
    private lateinit var debtId: String

    private val binding by lazy {
        ActivityDetailDebtBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val appComponent = (application as MyApplication).appComponent
        appComponent.inject(this)
        debtViewModel = ViewModelProvider(this, debtViewModelFactory).get(DebtViewModel::class.java)
        cardViewModel = ViewModelProvider(this, cardViewModelFactory).get(CardViewModel::class.java)
        paymentViewModel =
            ViewModelProvider(this, paymentViewModelFactory).get(PaymentViewModel::class.java)
        tryLoadDebt()
        updateUI()
        startService(Intent(this, BackgroundService::class.java))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val areNotificationsEnabled = notificationManager.areNotificationsEnabled()
        if (areNotificationsEnabled) {
            // Permissão de notificação concedida
        } else {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    R.string.channel_id.toString(),
                    "channelName",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }

        }

    }

    private fun updateUI() {
        if (intent.hasExtra("debtID")) {
            debtId = intent.getStringExtra("debtID").toString()
            debtViewModel.getDebtById(debtId).observe(this) { debtFlow ->
                lifecycleScope.launch {
                    debtFlow.collect { debt ->
                        if (debt != null) {
                            currentDebt = debt
                            with(binding) {
                                nameBuyerDetailDebtView.text = debt.nameBuyer
                                paidInstallmentsDetailDebtView.text =
                                    debt.paidInstallments.toString()
                                installmentsDetailDebtView.text = debt.installments.toString()
                                remainingPlotsDetailDebtView.text =
                                    (debt.installments - debt.paidInstallments).toString()
                                valueInstallmentsDetailDebtView.text =
                                    debt.valueInstallments.toBigDecimal()
                                        .formataParaMoedaBrasileira()
                                valueOfBuyDetailDebtView.text =
                                    debt.valueBuy.toBigDecimal().formataParaMoedaBrasileira()
                                unpaidDetailDebtView.text =
                                    (debt.valueInstallments * remainingPlotsDetailDebtView.text.toString()
                                        .toInt()).toBigDecimal().formataParaMoedaBrasileira()
                            }
                            paidInstallment()
                            getPayment()
                        } else {
                            Snackbar.make(
                                binding.root,
                                "Tivemos um problema para carregar o débito, tente novamente!",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun getPayment() {
        binding.recyclerPaymentDetailDebtView.layoutManager =
            LinearLayoutManager(this@DetailDebtActivity)
        binding.recyclerPaymentDetailDebtView.adapter = paymentAdapter
        paymentViewModel.payments.observe(this@DetailDebtActivity) { payments ->
            paymentAdapter.setPayments(payments)
        }
        val payments = paymentViewModel.payments
        Log.i("DetailDebtActivity:", payments.toString())
    }

    private fun paidInstallment() {
        paymentViewModel.getAllPayment(debtId)

        paymentAdapter = PaymentAdapter(
            onItemClick = { payment ->
                if (payment.paymentStatus == STATUS_PAGO) {
                    paymentViewModel.paidInstallment(payment, STATUS_CRIADO) { success ->
                        if (success) {
                            Snackbar.make(
                                binding.root,
                                "Parcela marcada como paga!",
                                Snackbar.LENGTH_LONG
                            ).show()
                        } else {
                            Snackbar.make(
                                binding.root,
                                "Tivemos um problema ao registrar o pagamento!",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                } else if (payment.paymentStatus == STATUS_CRIADO) {
                    paymentViewModel.paidInstallment(payment, STATUS_PAGO) { success ->
                        if (success) {
                            Snackbar.make(
                                binding.root,
                                "Parcela marcada como criada!",
                                Snackbar.LENGTH_LONG
                            ).show()
                        } else {
                            Snackbar.make(
                                binding.root,
                                "Tivemos um problema ao registrar o pagamento!",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            },
            paymentList = mutableListOf()
        )
    }

    private fun tryLoadDebt() {
        debtId = intent.getStringExtra(DEBT_KEY_ID).toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_debt, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editMenuDetailDebt -> {
                startActivity(Intent(this, EditFormDebtActivity::class.java).apply {
                    putExtra("debtID", debtId)
                })
            }

            R.id.deleteMenuDetailDebt -> {
                try {
                    Snackbar.make(binding.root, "Removendo dívida", Snackbar.LENGTH_LONG).show()
                    binding.progressBarDetailDebtView.visibility = View.VISIBLE
                    binding.viewDetailDebtView.visibility = View.VISIBLE
                    Handler(Looper.getMainLooper()).postDelayed({
                        lifecycleScope.launch {
                            if (isActive) {
                                debtViewModel.delete(debtId)
                                finish()
                            }
                        }
                    }, 3000)
                } catch (e: Exception) {
                    Snackbar.make(binding.root, "Erro ao apagar dívida!", Snackbar.LENGTH_LONG)
                        .show()
                }
            }

            R.id.noticaMenuDetailDebt ->{
                notificacao()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun notificacao() {
        // 1. Criar um canal de notificação (válido para Android 8.0 e versões posteriores)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = getString(R.string.channel_id)
            val channelName = "Channel Name"
            val channelDescription = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }

            // Registrar o canal de notificação no NotificationManager
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

// 2. Criar o conteúdo da notificação
        val notificationTitle = "Título da Notificação"
        val notificationText = "Texto da Notificação"
        val notificationIntent = Intent(this, DetailDebtActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(   this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(this, "channel_id")
            .setSmallIcon(R.drawable.ic_notifications_24)
            .setContentTitle(notificationTitle)
            .setContentText(notificationText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // Fechar a notificação ao ser clicada

// 3. Exibir a notificação
        val notificationId = 1
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notificationBuilder.build())


    }
}
