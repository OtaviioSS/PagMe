package com.pagme.app.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.pagme.app.database.DatabaseRef
import com.pagme.app.entity.User



class UserRepository {
    private var auth: FirebaseAuth = Firebase.auth
    private val curUser = auth.currentUser


    fun createUser(email: String, password: String, userName: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(userName).build()
                    auth.currentUser!!.updateProfile(profileUpdates)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                }
            }
    }

    fun getUser(): User {
        val user = User()
        user.email = curUser!!.email
        user.userName = curUser.displayName
        return user

    }

    fun updateUser(email: String, name: String): Boolean {
        return try {
            curUser!!.updateEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        curUser.updateProfile(userProfileChangeRequest { displayName = name })

                    }
                }

            true
        } catch (ex: Exception) {
            false
        }

    }

    fun loginUser(
        email: String,
        password: String,
        auth: FirebaseAuth,
        context: Context
    ): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() {
                Toast.makeText(context, "Usuario logado", Toast.LENGTH_LONG).show()

            }.addOnFailureListener { exception ->
                Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }


    }

    fun singOut() {
        Firebase.auth.signOut()

    }

    fun deleteUser() {
        curUser!!.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    singOut()
                }
            }
    }
}