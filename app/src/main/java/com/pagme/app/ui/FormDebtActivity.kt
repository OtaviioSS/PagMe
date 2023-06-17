package com.pagme.app.ui

import PermissionHandler
import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.R
import com.pagme.app.MyApplication
import com.pagme.app.data.model.Contact
import com.pagme.app.data.model.Debt
import com.pagme.app.databinding.ActivityFormDebtBinding
import com.pagme.app.extensions.MoneyTextWatcher
import com.pagme.app.extensions.MoneyTextWatcher.Companion.formatPriceSave
import com.pagme.app.presentation.viewmodel.CardViewModel
import com.pagme.app.presentation.viewmodel.ContactViewModel
import com.pagme.app.presentation.viewmodel.DebtViewModel
import com.pagme.app.util.CurrencyTextWatcher
import java.util.UUID

class FormDebtActivity : UserBaseActivity() {

    private lateinit var debtViewModel: DebtViewModel
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var cardViewModel: CardViewModel
    private lateinit var permissionHandler: PermissionHandler
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
        setupViewModel()
        setupViews()
        setupListeners()
        setupCardSpinner()
        permissionHandler = PermissionHandler(
            this,
            arrayOf(Manifest.permission.READ_CONTACTS),
            CONTACTS_PERMISSION_REQUEST_CODE
        ) { granted ->
            if (granted) {
                setupContactSpinner()
            } else {
                Toast.makeText(this, "Necessário permissão para ler os contatos", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun setupViewModel() {
        val appComponent = (application as MyApplication).appComponent
        appComponent.inject(this)
        val debtViewModelFactory = appComponent.provideDebtViewModelFactory()
        val cardtViewModelFactory = appComponent.provideCardViewModelFactory()
        val contactViewModelFactory = appComponent.provideContactViewModelFactory()
        debtViewModel = ViewModelProvider(this, debtViewModelFactory).get(DebtViewModel::class.java)
        cardViewModel =
            ViewModelProvider(this, cardtViewModelFactory).get(CardViewModel::class.java)
        contactViewModel =
            ViewModelProvider(this, contactViewModelFactory).get(ContactViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        permissionHandler.requestPermissions()
        setupCardSpinner()
        contactViewModel.getAllContacts(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHandler.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
                        formatPriceSave(binding.valueBuyNewDebtView.text.toString()).toDouble() /
                                binding.installmentsNewDebtView.text.toString().toInt()
                    binding.valueInstallmentsNewDebtView.setText(valorParcela.toString())
                } catch (e: Exception) {
                    Toast.makeText(
                        this,
                        "Problema ao calcular valor das parcelas, tente novamente por favor",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e("Exception Valparcela: ", "Erro: $e")
                }
            }
        }

        binding.buttonSaveNewDebtView.setOnClickListener {
            val debt = Debt(
                idDebt = UUID.randomUUID().toString(),
                nameCard = binding.spinnerCardNewDebtView.text.toString(),
                nameBuyer = nameBuy.orEmpty(),
                valueBuy = formatPriceSave(binding.valueBuyNewDebtView.text.toString()).toDouble(),
                installments = binding.installmentsNewDebtView.text.toString().toInt(),
                paidInstallments = 0,
                whatsapp = binding.whatsappNewDebtView.text.toString(),
                valueInstallments = binding.valueInstallmentsNewDebtView.text.toString().toDouble(),
                userId = ""
            )
            debtViewModel.createDebt(debt) { success ->
                runOnUiThread {
                    if (!success) {
                        Toast.makeText(this, "Não foi possível criar a dívida", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, "Dívida criada com sucesso", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }

        binding.buttonOpenNewCardActivity.setOnClickListener {
            startActivity(Intent(this, FormCardActivity::class.java))
        }

        binding.valueBuyNewDebtView.addTextChangedListener(CurrencyTextWatcher(binding.valueBuyNewDebtView, "R$"))
        binding.valueInstallmentsNewDebtView.addTextChangedListener(CurrencyTextWatcher(binding.valueInstallmentsNewDebtView, "R$"))
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
                        contactsList.firstOrNull { it.name == parent.getItemAtPosition(position) as String }
                    if (selectedContact != null) {
                        nameBuy = selectedContact.name
                        binding.whatsappNewDebtView.setText(selectedContact.phone)
                    }
                }

        }

    }

    private fun setupCardSpinner() {
        cardViewModel.cards.observe(this) { cards ->
            binding.spinnerCardNewDebtView.setAdapter(
                ArrayAdapter(
                    this@FormDebtActivity,
                    R.layout.support_simple_spinner_dropdown_item,
                    cards.map { it.nameCard })
            )
        }
        cardViewModel.getAllCards()
    }


}
