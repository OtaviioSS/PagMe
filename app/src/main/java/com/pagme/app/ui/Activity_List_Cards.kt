package com.pagme.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pagme.app.R
import com.pagme.app.adapter.CardAdapter
import com.pagme.app.business.CardBusiness
import com.pagme.app.entity.Card
import kotlinx.android.synthetic.main.activity_list_cards.*

class Activity_List_Cards : AppCompatActivity() {
    private lateinit var cardArrayList: ArrayList<Card>
    private lateinit var cardRecyclerView: RecyclerView
    private var cardBusiness = CardBusiness()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_cards)
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
        val cardsList = cardBusiness.readCards()
        cardRecyclerView.adapter = CardAdapter(cardsList)

    }




}