package com.pagme.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.pagme.app.R
import com.pagme.app.adapter.CardAdapter
import com.pagme.app.business.CardBussines
import com.pagme.app.entity.Card
import kotlinx.android.synthetic.main.activity_edit_card.*

class Activity_Edit_Card : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var cardArrayList: ArrayList<Card>
    private lateinit var cardRecyclerView: RecyclerView
    private var cardBussines = CardBussines()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_card)

        cardRecyclerView = recyclerCards
        cardRecyclerView.layoutManager = LinearLayoutManager(this)
        cardRecyclerView.setHasFixedSize(true)
        cardArrayList = arrayListOf<Card>()
        getCard()
    }

    private fun getCard() {
        val cardsList = cardBussines.readCards()
        cardRecyclerView.adapter = CardAdapter(cardsList)

    }
}