package com.pagme.app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pagme.app.data.model.Card
import com.pagme.app.databinding.ItemCardBinding
import com.pagme.app.databinding.ItemDebitBinding


class CardAdapter(
    private val cardList: List<Card>,
    private val onItemClickListener: CardAdapter.OnItemClickListener
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    var cards: List<Card> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            ItemCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cardList[position]
        holder.bind(card)
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(card)
        }

    }


    class CardViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(card: Card) {
            binding.textViewNameCardItemCardView.text = card.nameCard.toString()
            binding.textViewDueDateCardItemView.text = card.dueDate.toString()
        }

    }

    interface OnItemClickListener {
        fun onItemClick(card: Card)
    }

}