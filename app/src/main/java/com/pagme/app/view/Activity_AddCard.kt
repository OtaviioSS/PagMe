package com.pagme.app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pagme.app.R
import com.pagme.app.model.Card
import kotlinx.android.synthetic.main.activity_add_card.*
import java.util.*

private lateinit var database: DatabaseReference


class Activity_AddCard : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        database = Firebase.database.reference
        buttonSaveNewCardView.setOnClickListener() { newCard() }
    }

    /*Controller*/
    fun newCard() {
        val date = Calendar.getInstance().time
        val card = Card(
            date.toString(),
            nameNewCardView.text.toString(),
            closingNewCardView.text.toString(),
            dueDateNewCardView.text.toString()
        )
        /*DAO*/
        database.child("userOtavio").child("cards").child(card.cardID.toString()).setValue(card)
            .addOnSuccessListener {
                Toast.makeText(this, "Cartão adicionado", Toast.LENGTH_SHORT).show()
                nameNewCardView.setText("")
                closingNewCardView.setText("")
                dueDateNewCardView.setText("")
            }

    }


}