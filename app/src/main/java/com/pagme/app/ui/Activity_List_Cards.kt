package com.pagme.app.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.pagme.app.R
import com.pagme.app.adapter.CardAdapter
import com.pagme.app.entity.Card
import kotlinx.android.synthetic.main.activity_list_cards.*

class Activity_List_Cards : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var cardArrayList: ArrayList<Card>
    private lateinit var cardRecyclerView: RecyclerView
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_cards)
        auth = Firebase.auth
        cardRecyclerView = recyclerCards
        cardRecyclerView.layoutManager = LinearLayoutManager(this)
        cardRecyclerView.setHasFixedSize(true)
        cardArrayList = arrayListOf<Card>()
        getCard()

    }

    override fun onStart() {
        super.onStart()
        getCard()
    }

    private fun getCard() {
        database = FirebaseDatabase.getInstance().getReference(auth.currentUser!!.uid)
        database.child("cards").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cardArrayList.clear()
                for (cardSnapshot in snapshot.children) {
                    val card = cardSnapshot.getValue(Card::class.java)
                    cardArrayList.add(card!!)

                }
                cardRecyclerView.adapter = CardAdapter(cardArrayList)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_LONG).show()
            }
        })

    }




}