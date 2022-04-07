package com.pagme.app.repository

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.pagme.app.database.DatabaseRef
import com.pagme.app.entity.Debt
import com.pagme.app.entity.User
import com.pagme.app.ui.MainActivity
import kotlinx.android.synthetic.main.activity_edit_debt.*


class UserRepository {
    private val database = DatabaseRef().initializeDatabaseRefrence()

    fun createUser(user: User, auth: FirebaseAuth): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(
            user.email.toString(),
            user.passwordUser.toString()
        ).addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    database.child("users").child(user.userId.toString()).setValue(user)
                }
            }
    }

    fun getUser(userId: String): User {
        var user = User()
        database.child("user").child(userId).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (debtSnapshot in snapshot.children) {
                    user = debtSnapshot.getValue(User::class.java)!!

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return user

    }

    fun loginUser(email: String, password: String, auth: FirebaseAuth,context: Context): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() {
                Toast.makeText(context,"Usuario logado",Toast.LENGTH_LONG).show()

            }.addOnFailureListener { exception ->
                Toast.makeText(context,exception.localizedMessage,Toast.LENGTH_LONG).show()
            }


    }

    fun singOut() {
        Firebase.auth.signOut()
    }
}