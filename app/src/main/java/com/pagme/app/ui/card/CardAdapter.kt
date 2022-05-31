package com.pagme.app.ui.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pagme.app.databinding.ItemCardBinding
import com.pagme.app.domain.model.Card

class CardAdapter() : ListAdapter<Card, CardAdapter.CardViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    class CardViewHolder(
        private val itemBinding: ItemCardBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(card: Card) {
            itemBinding.run {
                nameCardItem.setText(card.cardName)
                closingCardItem.setText(card.closingDate)
                dueDateCardItem.setText(card.dueDate)

            }
        }

        companion object {
            fun create(parent: ViewGroup): CardViewHolder {
                val itemBinding = ItemCardBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)

                return CardViewHolder(itemBinding)
            }
        }


    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(
                oldItem: Card,
                newItem: Card
            ): Boolean {
                return oldItem.cardID == newItem.cardID
            }

            override fun areContentsTheSame(
                oldItem: Card,
                newItem: Card
            ): Boolean {
                return oldItem == newItem
            }

        }
    }


}