package com.pagme.app.data.user

import android.content.ContentValues
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.pagme.app.domain.model.User
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class FirebaseUserDataSource @Inject constructor(
    databaseRef: DatabaseReference,
    firebaseAuth: FirebaseAuth
) : UserDataSource {

    private val auth = firebaseAuth
    private val database = databaseRef
    private val currentUser = firebaseAuth.currentUser

    override suspend fun createUser(email: String, password: String, userName: String): Task<AuthResult>{
        return auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(userName).build()
                    auth.currentUser!!.updateProfile(profileUpdates)
                } else {
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                }
            }
    }

    override suspend fun getUser(): User {
        val user = User()
        user.email = currentUser!!.email
        user.userName = currentUser.displayName
        return user
    }

    override suspend fun updateUser(email: String, name: String): Boolean {
        return suspendCoroutine { continuation ->
            auth.useAppLanguage()
            currentUser!!.updateEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        currentUser.updateProfile(userProfileChangeRequest { displayName = name })
                        continuation.resumeWith(Result.success(true))
                    }

                }.addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }
        }

    }

    override suspend fun loginUser(email: String, password: String): Task<AuthResult> {
        return suspendCoroutine { continuation ->
            auth.useAppLanguage()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    continuation.resumeWith(Result.success(task))
                }.addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }
        }
    }

    override suspend fun singOut() {
        auth.useAppLanguage()
        Firebase.auth.signOut()
    }

    override suspend fun deleteUser() {
        currentUser!!.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Firebase.auth.signOut()
                }
            }
    }
}