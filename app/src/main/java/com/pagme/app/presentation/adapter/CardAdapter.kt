package com.pagme.app.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pagme.app.data.model.Card
import com.pagme.app.databinding.ItemCardBinding
import com.pagme.app.databinding.ItemDebitBinding
import java.util.Collections


class CardAdapter(private var cardList: MutableList<Card>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {


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
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCard(cards: MutableList<Card>) {
        this.cardList = cards
        notifyDataSetChanged()
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

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(cardList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    fun deleteItem(position: Int) {
        cardList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getCardAtPosition(position: Int): Card {
        return cardList[position]
    }

}