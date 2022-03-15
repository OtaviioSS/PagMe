package com.pagme.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pagme.app.R
import com.pagme.app.business.CardBussines
import com.pagme.app.entity.Card
import com.pagme.app.repository.CardRepository
import kotlinx.android.synthetic.main.activity_add_card.*
import java.util.*

private lateinit var database: DatabaseReference
private var cardBussines = CardBussines()



class Activity_AddCard : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        database = Firebase.database.reference
        buttonSaveNewCardView.setOnClickListener() { newCard() }
    }

    fun newCard() {
        val date = Calendar.getInstance().time
        val card = Card(
            date.toString(),
            nameNewCardView.text.toString(),
            closingNewCardView.text.toString(),
            dueDateNewCardView.text.toString()
        )
        val resultBussines = cardBussines.createCard(card)
        if (resultBussines){
            Toast.makeText(this,R.string.cartao_inserido,Toast.LENGTH_LONG).show()
        }
        Toast.makeText(this,R.string.erro_inserir_cartao,Toast.LENGTH_LONG).show()



    }


}