package com.pagme.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.pagme.app.R
import com.pagme.app.adapter.DebtAdapter
import com.pagme.app.entity.Debt
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var debtArrayList: ArrayList<Debt>
    private lateinit var debtRecyclerView: RecyclerView
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setBackgroundResource(R.drawable.fab_background)


        toggle = ActionBarDrawerToggle(this, main, R.string.open, R.string.close)
        main.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        navigationViewMainView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.myCardsMenuDrawaerMain ->
                    startActivity(Intent(applicationContext, Activity_List_Cards::class.java))

            }
            true
        }

        debtRecyclerView = recyclerDebits
        debtRecyclerView.layoutManager = LinearLayoutManager(this)
        debtRecyclerView.setHasFixedSize(true)
        debtArrayList = arrayListOf<Debt>()
        getDabts()
        mudarTela()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getDabts() {
        database = FirebaseDatabase.getInstance().getReference("userOtavio")
        database.child("debts").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (debtSnapshot in snapshot.children) {
                    val debt = debtSnapshot.getValue(Debt::class.java)
                    debtArrayList.add(debt!!)
                }
                debtRecyclerView.adapter = DebtAdapter(debtArrayList)


            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun mudarTela() {
        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {

            val intent = Intent(this, Activity_New_Debit::class.java)
            startActivity(intent)
        }

    }


}