package com.pagme.app.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.pagme.app.data.AppDatabase
import com.pagme.app.domain.model.User
import com.pagme.app.extensions.goTo
import com.pagme.app.preferences.dataStore
import com.pagme.app.preferences.userLoggedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.function.LongFunction

abstract class UserBaseActivity : AppCompatActivity() {
    private val userDao by lazy {
        AppDatabase.instance(this).userDao()
    }


    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    protected val user: StateFlow<User?> = _user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            checkLoggedUser()

        }
    }


    private suspend fun getUserID(userId: String): User? {
        return userDao.searchById(userId).firstOrNull().also {
            _user.value = it
        }
    }

    private fun goToLogin() {
        goTo(LoginActivity::class.java) {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        finish()
    }

    private suspend fun checkLoggedUser() {
        dataStore.data.collect { preference ->
            preference[userLoggedPreferences]?.let { userId ->
                getUserID(userId)
            }?: goToLogin()

        }
    }

    protected suspend fun logoutUser() {
        dataStore.edit { preferences ->
            preferences.remove(userLoggedPreferences)
        }
    }
}