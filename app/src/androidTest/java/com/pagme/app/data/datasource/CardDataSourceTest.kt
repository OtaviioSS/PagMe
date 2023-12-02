package com.pagme.app.data.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.Transaction
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.pagme.app.data.model.Card
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class CardDataSourceTest {

    private lateinit var dataSource: CardDataSource
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    @Before
    fun setup() {
        db = mock(FirebaseFirestore::class.java)
        auth = mock(FirebaseAuth::class.java)
        dataSource = CardDataSource()
    }

    @Test
    fun createCard_success(): Unit = runBlocking {
        val card = Card(
            idCard = "1",
            nameCard = "Card 1"
        )
        val documentReference = mock(DocumentReference::class.java)
        `when`(db.collection(any()).document(any()).collection(any()).document(any()))
            .thenReturn(documentReference)
        `when`(documentReference.set(any())).thenAnswer { invocation ->
            val argument = invocation.arguments[0]
            if (argument is Map<*, *>) {
                @Suppress("UNCHECKED_CAST")
                val task = mock(Task::class.java) as Task<Void>
                task
            } else {
                throw IllegalArgumentException("Invalid argument type")
            }
        }


        dataSource.create(card)

        verify(db.collection(any()).document(any()).collection(any()).document(any())).set(card)
    }

    @Test(expected = Exception::class)
    fun createCard_failure()= runBlocking {
        val card = Card(
            idCard = "1",
            nameCard = "Card 1"
        )
        val documentReference = mock(DocumentReference::class.java)
        `when`(db.collection(any()).document(any()).collection(any()).document(any()))
            .thenReturn(documentReference)
        `when`(documentReference.set(any())).thenThrow(Exception("Mock Exception"))

        dataSource.create(card)
    }


}
