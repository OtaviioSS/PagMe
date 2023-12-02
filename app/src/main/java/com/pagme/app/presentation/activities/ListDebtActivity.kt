package com.pagme.app.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pagme.app.MyApplication
import com.pagme.app.R
import com.pagme.app.data.model.Debt
import com.pagme.app.databinding.ActivityListDebtBinding
import com.pagme.app.presentation.AlarmHelper
import com.pagme.app.presentation.adapter.DebtAdapter
import com.pagme.app.presentation.service.NotificationService
import com.pagme.app.presentation.viewmodel.DebtViewModel
import kotlinx.coroutines.launch


class ListDebtActivity : UserBaseActivity(), DebtAdapter.OnItemClickListener {

    private lateinit var debtViewModel: DebtViewModel
    private lateinit var adapter: DebtAdapter


    private val binding by lazy {
        ActivityListDebtBinding.inflate(layoutInflater)
    }

    private lateinit var alarmHelper: AlarmHelper

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth
        if (auth.currentUser == null) {
            startActivity(
                Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finish()
        }
        val appComponent = (application as MyApplication).appComponent
        appComponent.inject(this)
        val viewModelFactory = appComponent.provideDebtViewModelFactory()
        debtViewModel = ViewModelProvider(this, viewModelFactory)[DebtViewModel::class.java]
        adapter = DebtAdapter(emptyList(), emptyList())
        adapter.setOnItemClickListener(this)
        binding.recyclerDebits.layoutManager = LinearLayoutManager(this)
        binding.recyclerDebits.adapter = adapter
        var debtsList: ArrayList<Debt> = ArrayList()
        debtViewModel.debts.observe(this) { debts ->
            adapter.setDebts(debts)
            debtsList = debts as ArrayList<Debt>
        }
        debtViewModel.getAllDebts()

        configFabButton()
     /*   val intent = Intent(this, NotificationService::class.java)
        intent.putExtra("debts", debtsList)
        startService(intent)
        alarmHelper = AlarmHelper(this)
        alarmHelper.scheduleAlarms()*/


    }

    private fun filterList(query: String) {
        if (query.isNotEmpty()) {
            debtViewModel.filterDebts(query)
        } else {
            debtViewModel.getAllDebts()
        }
    }


    public override fun onStart() {
        super.onStart()
        debtViewModel.getAllDebts()


    }


    private fun configFabButton() {
        binding.activityListDebtFab.setOnClickListener {
            startActivity(Intent(this, FormDebtActivity::class.java))
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_drawer_main_activity, menu)

        val searchItem = menu!!.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Lógica a ser executada quando o usuário submeter a pesquisa
                filterList(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterList(newText)
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mySingOut -> {
                Firebase.auth.signOut()
                startActivity(
                    Intent(
                        this,
                        LoginActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                finish()

            }

            R.id.myAccountMenuDrawaerMain -> {
                lifecycleScope.launch {
                    user?.let {
                        startActivity(
                            Intent(this@ListDebtActivity, ProfileUserActivity::class.java)
                        )
                    }
                }


            }

            R.id.myCardsMenuDrawaerMain -> {
                startActivity(
                    Intent(this, ListCardActivity::class.java)
                )
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(debt: Debt) {
        val intent = Intent(this, DetailDebtActivity::class.java)
        intent.putExtra("debtID", debt.idDebt)
        startActivity(intent)
    }

}