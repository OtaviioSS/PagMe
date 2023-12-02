package com.pagme.app.presentation.activities

import PermissionHandler
import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.R
import com.google.android.material.snackbar.Snackbar
import com.pagme.app.MyApplication
import com.pagme.app.data.model.Debt
import com.pagme.app.data.model.Payment
import com.pagme.app.databinding.ActivityFormDebtBinding
import com.pagme.app.presentation.viewmodel.CardViewModel
import com.pagme.app.presentation.viewmodel.ContactViewModel
import com.pagme.app.presentation.viewmodel.DebtViewModel
import com.pagme.app.util.CurrencyTextWatcher
import com.pagme.app.util.STATUS_CRIADO
import com.pagme.app.util.extensions.MoneyTextWatcher
import com.pagme.app.util.extensions.MoneyTextWatcher.Companion.formatPriceSave
import java.util.Calendar
import java.util.Date
import java.util.UUID

class FormDebtActivity : UserBaseActivity() {

    private lateinit var debtViewModel: DebtViewModel
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var cardViewModel: CardViewModel
    private lateinit var permissionHandler: PermissionHandler
    private var cardID: String = ""
    private var cardDueDate: Int = 0
    private val CONTACTS_PERMISSION_REQUEST_CODE = 1

    private val binding by lazy {
        ActivityFormDebtBinding.inflate(layoutInflater)
    }

    private var valorParcela: Double = 0.0
    private var nameBuy: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastro de Dívida"
        permissionHandler = PermissionHandler(
            this,
            arrayOf(Manifest.permission.READ_CONTACTS),
            CONTACTS_PERMISSION_REQUEST_CODE
        ) { granted ->
            if (granted) {
                setupContactSpinner()
                contactViewModel.getAllContacts(this)
            } else {
                Toast.makeText(this, "Necessário permissão para ler os contatos", Toast.LENGTH_LONG)
                    .show()
            }
        }
        setupViewModel()
        setupViews()
        setupListeners()
        setupCardSpinner()


    }


    override fun onStart() {
        super.onStart()
        permissionHandler.requestPermissions()
        setupCardSpinner()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHandler.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setupViewModel() {
        val appComponent = (application as MyApplication).appComponent
        appComponent.inject(this)
        val debtViewModelFactory = appComponent.provideDebtViewModelFactory()
        val cardViewModelFactory = appComponent.provideCardViewModelFactory()
        val contactViewModelFactory = appComponent.provideContactViewModelFactory()
        debtViewModel = ViewModelProvider(this, debtViewModelFactory)[DebtViewModel::class.java]
        cardViewModel = ViewModelProvider(this, cardViewModelFactory)[CardViewModel::class.java]
        contactViewModel =
            ViewModelProvider(this, contactViewModelFactory)[ContactViewModel::class.java]
    }

    private fun setupViews() {
        binding.valueBuyNewDebtView.addTextChangedListener(MoneyTextWatcher(binding.valueBuyNewDebtView))
        binding.valueBuyNewDebtView.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
    }

    private fun setupListeners() {
        binding.installmentsNewDebtView.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                try {
                    valorParcela =
                        formatPriceSave(binding.valueBuyNewDebtView.text.toString()).toDouble() / binding.installmentsNewDebtView.text.toString()
                            .toInt()
                    binding.valueInstallmentsNewDebtView.setText(valorParcela.toString())
                } catch (e: Exception) {
                    valorParcela = 0.0
                }
            }
        }

        binding.buttonSaveNewDebtView.setOnClickListener {
            val installmentsText = binding.installmentsNewDebtView.text.toString()
            val valueBuyText = binding.valueBuyNewDebtView.text.toString()
            val valueInstallmentsText = binding.valueInstallmentsNewDebtView.text.toString()
            val whatsappText = binding.whatsappNewDebtView.text.toString()
            val cardNameText = binding.spinnerCardNewDebtView.text.toString()
            if (installmentsText.isEmpty() || valueBuyText.isEmpty() || valueInstallmentsText.isEmpty() || whatsappText.isEmpty() || cardNameText.isEmpty()) {
                showSnackbar("Todos os campos são obrigatórios!")
            } else {
                try {
                    val debtID = UUID.randomUUID().toString()
                    val installments = installmentsText.toInt()
                    val payments = createPayments(debtID, installments, cardDueDate)
                    val buyerName = nameBuy.orEmpty()
                    val cardID = cardID
                    val valueBuy = formatPriceSave(valueBuyText).toDouble()
                    val valueInstallments = valueInstallmentsText.toDouble()
                    val debt = Debt(
                        idDebt = debtID,
                        nameCard = cardNameText,
                        nameBuyer = buyerName,
                        cardID = cardID,
                        valueBuy = valueBuy,
                        installments = installments,
                        paidInstallments = 0,
                        whatsapp = whatsappText,
                        valueInstallments = valueInstallments,
                        userId = ""
                    )

                    debtViewModel.createDebt(debt, payments) { success ->
                        runOnUiThread {
                            if (!success) {
                                showSnackbar("Não foi possível criar a dívida!")
                            } else {
                                showSnackbar("Dívida criada!")
                            }
                        }
                    }
                } catch (e: Exception) {
                    showSnackbar("Erro ao criar o débito, tente novamente.")

                }
            }
        }



        binding.buttonOpenNewCardActivity.setOnClickListener {
            startActivity(Intent(this, FormCardActivity::class.java))
        }

        binding.valueBuyNewDebtView.addTextChangedListener(
            CurrencyTextWatcher(
                binding.valueBuyNewDebtView,
                "R$"
            )
        )
        binding.valueInstallmentsNewDebtView.addTextChangedListener(
            CurrencyTextWatcher(
                binding.valueInstallmentsNewDebtView,
                "R$"
            )
        )
    }

    private fun createPayments(
        debtID: String,
        installments: Int,
        dueDate: Int
    ): ArrayList<Payment> {
        val paymentsList = ArrayList<Payment>()
        val currentDate = Date()
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        if (calendar.get(Calendar.DAY_OF_MONTH) > dueDate) {
            calendar.add(Calendar.MONTH, 1)
        }
        calendar.set(Calendar.DAY_OF_MONTH, dueDate)
        var dueDatePayment = calendar.time
        for (i in 1.rangeTo(installments)) {
            paymentsList.add(
                Payment(
                    paymentID = UUID.randomUUID().toString(),
                    paymentDate = currentDate,
                    paymentDueDate = dueDatePayment,
                    paymentValue = binding.valueInstallmentsNewDebtView.text.toString().toDouble(),
                    paymentStatus = STATUS_CRIADO,
                    debtID = debtID,
                    cardID = cardID
                )
            )
            calendar.add(Calendar.MONTH, 1)
            dueDatePayment = calendar.time
        }

        return paymentsList
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        binding.progressBarFormDebtView.visibility = View.VISIBLE
        binding.viewFormDebtView.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 3000)
    }

    private fun setupContactSpinner() {
        contactViewModel.contacts.observe(this) { contactsList ->
            val contactNames = contactsList.map { it.name }
            val spinnerAdapter =
                ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, contactNames)
            binding.spinnerContact.setAdapter(spinnerAdapter)

            binding.spinnerContact.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, position, _ ->
                    val selectedContact =
                        contactsList.find { it.name == parent.getItemAtPosition(position) as String }
                    if (selectedContact != null && selectedContact.phone.size > 1) {
                        nameBuy = selectedContact.name
                        val phoneNumber = selectedContact.phone[1]
                        binding.whatsappNewDebtView.setText(phoneNumber)
                    } else if (selectedContact != null) {
                        nameBuy = selectedContact.name
                        val phoneNumber = selectedContact.phone.toString()
                        binding.whatsappNewDebtView.setText(phoneNumber)
                    }
                }


        }

    }

    private fun setupCardSpinner() {
        cardViewModel.cards.observe(this) { cards ->
            val cardNames = cards.map { it.nameCard }
            val adapter = ArrayAdapter(
                this@FormDebtActivity,
                R.layout.support_simple_spinner_dropdown_item,
                cardNames
            )
            binding.spinnerCardNewDebtView.setAdapter(adapter)

            binding.spinnerCardNewDebtView.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, position, _ ->
                    val selectedCardName = parent.getItemAtPosition(position) as String
                    val selectedCard = cards.find { it.nameCard == selectedCardName }
                    cardID = selectedCard!!.idCard
                    cardDueDate = selectedCard.dueDate

                }
        }

        cardViewModel.getAllCards()
    }


}
