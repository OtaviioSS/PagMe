package com.pagme.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.pagme.app.R
import com.pagme.app.adapter.DebtAdapter
import com.pagme.app.business.UserBusiness
import com.pagme.app.entity.Debt
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var debtArrayList: ArrayList<Debt>
    private lateinit var debtRecyclerView: RecyclerView
    private lateinit var toggle: ActionBarDrawerToggle
    private val userBusiness = UserBusiness()
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        verifyAuthentication()
        fab.setBackgroundResource(R.drawable.fab_background)

        toggle = ActionBarDrawerToggle(this, main, R.string.open, R.string.close)
        main.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        navigationViewMainView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.myCardsMenuDrawaerMain ->
                    startActivity(Intent(applicationContext, Activity_List_Cards::class.java))
                R.id.myAccountMenuDrawaerMain ->
                    startActivity(Intent(applicationContext, Activity_My_Profile::class.java))
                R.id.mySingOut ->
                    if (auth.currentUser != null) {
                        userBusiness.logOutUser()
                        startActivity(Intent(applicationContext, Activity_Login::class.java))
                        Toast.makeText(this, "Usuario deconectado", Toast.LENGTH_LONG).show()
                        finish()
                    }


            }
            true
        }
        val layoutManager: LinearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        debtRecyclerView = recyclerDebits
        debtRecyclerView.layoutManager = layoutManager
        debtRecyclerView.setHasFixedSize(true)
        debtArrayList = arrayListOf<Debt>()

        getDebts()
        openActivity()

    }

    private fun verifyAuthentication() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this, Activity_Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    public override fun onStart() {
        super.onStart()
        verifyAuthentication()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getDebts() {
        database = FirebaseDatabase.getInstance().getReference(auth.currentUser!!.uid)
        database.child("debts").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                debtArrayList.clear()
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

    private fun openActivity() {
        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, Activity_New_Debit::class.java)
            startActivity(intent)
        }

    }


}