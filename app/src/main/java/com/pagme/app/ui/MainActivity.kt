package com.pagme.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
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
    private lateinit var debtArrayList : ArrayList<Debt>
    private lateinit var debtRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        debtRecyclerView = recyclerDebits
        debtRecyclerView.layoutManager = LinearLayoutManager(this)
        debtRecyclerView.setHasFixedSize(true)
        debtArrayList = arrayListOf<Debt>()
        getUserData()

        mudarTela()
    }
    private fun getUserData() {
        database = FirebaseDatabase.getInstance().getReference("userOtavio")
        database.child("debts").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                    for (debtSnapshot in snapshot.children){
                        val debt = debtSnapshot.getValue(Debt::class.java)
                        debtArrayList.add(debt!!)
                    }
                    debtRecyclerView.adapter = DebtAdapter(debtArrayList)


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
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