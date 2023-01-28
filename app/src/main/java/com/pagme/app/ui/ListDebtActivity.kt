package com.pagme.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.pagme.app.R
import com.pagme.app.data.AppDatabase
import com.pagme.app.databinding.ActivityListDebtBinding
import com.pagme.app.ui.recyclerview.ListDebtsAdapter
import com.pagme.app.util.DEBT_KEY_ID
import com.pagme.app.util.USER_KEY_ID
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ListDebtActivity : UserBaseActivity() {

    private val adapter = ListDebtsAdapter(context = this)

    private val binding by lazy {
        ActivityListDebtBinding.inflate(layoutInflater)
    }

    private val daoDebt by lazy {
        val database = AppDatabase.instance(this)
        database.debtDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configRecyclerView()
        configFabButton()
        lifecycleScope.launch() {
            launch {
                user
                    .filterNotNull()
                    .collect { user ->
                        getDebts(user.userId)

                    }
            }

        }
    }

    private suspend fun getDebts(userId: String) {
        daoDebt.getAllDebtUser(userId).collect { debts ->
            adapter.update(debts)

        }

    }


    private fun configFabButton() {
        binding.activityListDebtFab.setOnClickListener {
            startActivity(Intent(this, FormDebtActivity::class.java))
        }
    }


    private fun configRecyclerView() {
        binding.recyclerDebits.adapter = adapter
        adapter.clickItem = {
            startActivity(Intent(
                this,
                DetailDebtActivity::class.java
            ).apply {
                putExtra(DEBT_KEY_ID, it.idDebt)
            })

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_drawer_main_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mySingOut -> {
                lifecycleScope.launch {
                    logoutUser()
                }
            }
            R.id.myAccountMenuDrawaerMain -> {
                lifecycleScope.launch {
                    user.filterNotNull().collect{ user ->
                        startActivity(Intent(this@ListDebtActivity, ProfileUserActivity::class.java).apply {
                            putExtra(USER_KEY_ID, user.userId)
                        })
                    }
                }


            }

        }
        return super.onOptionsItemSelected(item)
    }

}