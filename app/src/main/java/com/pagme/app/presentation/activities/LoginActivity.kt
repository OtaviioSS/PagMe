package com.pagme.app.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pagme.app.R
import com.pagme.app.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }


    private lateinit var auth: FirebaseAuth
    private lateinit var googleApiClient: GoogleApiClient
    private val RC_SIGN_IN = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        listeners()
        auth = Firebase.auth
        if (auth.currentUser != null) {
            startActivity(
                Intent(this, ListDebtActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finish()
        }
        setupGoogleSignIn()

    }

    private fun listeners() {
        with(binding) {
          buttonSaveLoginView.setOnClickListener {
                authenticate(
                    emailLoginView.text.toString(),
                    passwordLoginView.text.toString()
                )
            }

            textRegisterLoginView.setOnClickListener {
                startActivity(Intent(this@LoginActivity, FormUserActivity::class.java))

            }

            textViewForgetPasswordLoginView.setOnClickListener {
                startActivity(Intent(this@LoginActivity, ForgetPasswordActivity::class.java))
            }

            buttonGoogleLogin.setOnClickListener {
                signInWithGoogle()
            }
        }
    }

    private fun authenticate(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@LoginActivity) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, ListDebtActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        Snackbar.make(binding.root, "Falha na autenticação!", Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
        } catch (e: Exception) {
            Snackbar.make(binding.root, "Erro ao tentar logar!", Snackbar.LENGTH_LONG).show()
        }


    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this) {
                // Tratar erros de conexão
            }
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
    }

    private fun signInWithGoogle() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // ...

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
            if (result!!.isSuccess) {
                val account = result.signInAccount
                firebaseAuthWithGoogle(account)
            } else {
                Snackbar.make(binding.root, "Falha na autenticação!", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, ListDebtActivity::class.java))
                    finish()
                } else {
                    Snackbar.make(binding.root, "Falha na autenticação!", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
    }

}