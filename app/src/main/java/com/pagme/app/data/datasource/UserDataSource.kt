package com.pagme.app.data.datasource

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.pagme.app.data.model.User
import com.pagme.app.util.CreateUserResult
import kotlinx.coroutines.tasks.await




class UserDataSource {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth = Firebase.auth


    suspend fun create(user: User): CreateUserResult {
        return try {
            if (this.checkIfEmailExists(user.email)) {
                CreateUserResult.EmailExists("Email já cadastrado!")
            } else {
                val authResult = auth.createUserWithEmailAndPassword(user.email, user.password).await()
                user.userId = authResult.user!!.uid
                authResult.user?.sendEmailVerification()?.await()
                if (authResult != null) {
                    user.password = ""
                    db.collection("users").document(auth.currentUser!!.uid).collection("dataUser")
                        .document("profileData")
                        .set(user)
                        .await()
                    CreateUserResult.Success("Usuário criado com sucesso!")
                } else {
                    CreateUserResult.Failure("Falha ao criar usuário")
                }
            }
        } catch (e: FirebaseAuthWeakPasswordException) {
            CreateUserResult.WeakPassword("A senha fornecida é fraca.")
        } catch (e: Exception) {
            CreateUserResult.Failure("Falha ao salvar dados: ${e.message}")
        }
    }

    suspend fun checkIfEmailExists(email: String): Boolean {
        try {
            val result = auth.fetchSignInMethodsForEmail(email).await()
            return result.signInMethods?.isNotEmpty() == true
        } catch (e: Exception) {
            throw Exception("Falha ao verificar email: ${e.message}")
        }
    }


    suspend fun alter(user: User) {
        try {
            db.collection("users").document(auth.currentUser!!.uid).collection("dataUser").document("profileData").update(mapOf("email" to user.email, "userName" to user.userName)).await()
        } catch (e: Exception) {
            throw Exception("Falha ao atualizar dados: ${e.message}")
        }

    }

    suspend fun remove() {
        try {
            db.collection("users").document(auth.currentUser!!.uid).delete()
                .addOnCompleteListener { dbTask ->
                    if (dbTask.isSuccessful) {
                        auth.currentUser!!.delete()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d("UserDataSource", "User account deleted.")
                                } else {
                                    Log.d("UserDataSource", "User account not deleted.")

                                }
                            }
                    } else {
                        Log.i(
                            "UserDataSource remove:",
                            "Falha ao remover dados do usuario: ${dbTask}"
                        )
                    }

                }.await()
        } catch (e: Exception) {
            throw Exception("Falha ao apagar a conta do usuario: ${e.message}")
        }

    }

    suspend fun getUserById(): User? {
        try {
            val snapshot =
                db.collection("users").document(auth.currentUser!!.uid).collection("dataUser")
                    .document("profileData").get().await()
            return snapshot.toObject(User::class.java)

        } catch (e: Exception) {
            throw Exception("Falha ao recuperar dados do usuario: ${e.message}")
        }

    }

    suspend fun userVerified(): Boolean {
        return auth.currentUser!!.isEmailVerified
    }

}