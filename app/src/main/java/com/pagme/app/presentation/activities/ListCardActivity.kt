package com.pagme.app.presentation.activities

import android.content.Intent
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pagme.app.MyApplication
import com.pagme.app.R
import com.pagme.app.databinding.ActivityListCardBinding
import com.pagme.app.presentation.adapter.CardAdapter
import com.pagme.app.presentation.adapter.SwipeToDeleteCallback
import com.pagme.app.presentation.viewmodel.CardViewModel


class ListCardActivity : UserBaseActivity() {

    private lateinit var cardViewModel: CardViewModel
    private lateinit var cardAdapter: CardAdapter

    private val binding by lazy {
        ActivityListCardBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val appComponent = (application as MyApplication).appComponent
        appComponent.inject(this)
        val viewModelFactory = appComponent.provideCardViewModelFactory()
        cardViewModel = ViewModelProvider(this, viewModelFactory)[CardViewModel::class.java]
        cardViewModel.getAllCards()

        cardAdapter = CardAdapter(mutableListOf())

        binding.recyclerCards.layoutManager = LinearLayoutManager(this)
        binding.recyclerCards.adapter = cardAdapter

        cardViewModel.cards.observe(this, Observer { cards ->
            cardAdapter.setCard(cards)
        })


        val swipeHandler = object : SwipeToDeleteCallback(cardAdapter) {
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val card = cardAdapter.getCardAtPosition(position)
                cardViewModel.delete(card)
                Toast.makeText(this@ListCardActivity, "Cartão excluido!", Toast.LENGTH_LONG).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerCards)

        binding.fabListCardView.setOnClickListener {
            startActivity(Intent(this, FormCardActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        cardViewModel.getAllCards()
    }
}