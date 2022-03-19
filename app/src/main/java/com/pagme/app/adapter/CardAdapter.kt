package com.pagme.app.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pagme.app.R
import com.pagme.app.entity.Card
import com.pagme.app.entity.Debt
import com.pagme.app.ui.Activity_Edit_Debt
import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.android.synthetic.main.item_debit.view.*

class CardAdapter(private val cardtList: ArrayList<Card>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cardtList[position]
        holder.idCard = card.cardID.toString()
        holder.nameCard.setText(card.cardName)
        holder.closignCard.setText(card.closingDate)
        holder.dueDateCard.setText(card.dueDate)

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
        val btnDelete: Button = itemView.btnDeleteCardItem

        init {
            when (btnEdit.text) {
                "Editar" -> btnEdit.setOnClickListener {
                    nameCard.isEnabled = true
                    closignCard.isEnabled = true
                    dueDateCard.isEnabled = true
                    btnEdit.text = "Salvar"
                }
                "Salvar" -> btnEdit.setOnClickListener {
                    nameCard.isEnabled = false
                    closignCard.isEnabled = false
                    dueDateCard.isEnabled = false
                    btnEdit.text = "Editar"
                }

            }


        }
    }


}