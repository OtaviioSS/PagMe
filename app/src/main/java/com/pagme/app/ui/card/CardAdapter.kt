package com.pagme.app.ui.card

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.pagme.app.R
import com.pagme.app.business.CardBusiness
import com.pagme.app.domain.model.Card
import kotlinx.android.synthetic.main.item_card.view.*

class CardAdapter(private val cardtList: ArrayList<Card>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    private var card = Card()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        card = cardtList[position]
        holder.idCard = card.cardID.toString()
        holder.nameCard.setText(card.cardName)
        holder.closignCard.setText(card.closingDate)
        holder.dueDateCard.setText(card.dueDate)
        holder.btnDelete.setOnClickListener(){
            holder.cardBussines.removeCard(card.cardID.toString())

        }
        holder.btnEdit.setOnClickListener(){
            if (holder.btnEdit.text.equals("Editar")) {
                holder.nameCard.isEnabled = true
                holder.closignCard.isEnabled = true
                holder.dueDateCard.isEnabled = true
                holder.btnEdit.text = "Salvar"

            } else if (holder.btnEdit.text.equals("Salvar")) {
                holder.nameCard.isEnabled = false
                holder.closignCard.isEnabled = false
                holder.dueDateCard.isEnabled = false
                holder.btnEdit.text = "Editar"
                val cardAlter = Card(
                    card.cardID.toString(),
                    holder.nameCard.text.toString(),
                    holder.closignCard.text.toString(),
                    holder.dueDateCard.text.toString()
                )
                holder.cardBussines.updateCard(cardAlter)



            }
        }

    }

    override fun getItemCount(): Int {
        return cardtList.size
    }


    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var idCard: String = ""
        var nameCard: EditText = itemView.nameCardItem
        val closignCard: EditText = itemView.closingCardItem
        val dueDateCard: EditText = itemView.dueDateCardItem
        val btnEdit: Button = itemView.btnEditCardItem
        val btnDelete: ImageButton = itemView.btnDeleteCardItem
        val cardBussines = CardBusiness()


    }



}