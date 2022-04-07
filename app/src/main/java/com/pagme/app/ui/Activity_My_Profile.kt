package com.pagme.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pagme.app.R
import com.pagme.app.business.DebtBusiness
import com.pagme.app.business.UserBusiness
import kotlinx.android.synthetic.main.activity_my_profile.*

class Activity_My_Profile : AppCompatActivity() {
    private var userBusiness = UserBusiness()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        auth = Firebase.auth
        val user =  userBusiness.getUser(auth.currentUser!!.uid)
        emailMyProfileView.setText(user.email.toString())
        userNameMyProfileView.setText(user.userName.toString())

    }


}