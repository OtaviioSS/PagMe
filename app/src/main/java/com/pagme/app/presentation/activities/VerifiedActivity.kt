package com.pagme.app.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pagme.app.databinding.ActivityVerifiedBinding

class VerifiedActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityVerifiedBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginButtonVerifiedView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}