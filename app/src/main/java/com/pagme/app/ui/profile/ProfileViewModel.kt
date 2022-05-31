package com.pagme.app.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pagme.app.data.user.UserRepository
import com.pagme.app.domain.model.User
import com.pagme.app.domain.usecase.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {


    private val _profileData = MutableLiveData<User>()
    val profileData: LiveData<User> = _profileData

    fun getProfile() = viewModelScope.launch {
        try {
            val user = getUserUseCase()
            _profileData.value = user
        } catch (e: Exception) {
            Log.d("UserViewModel", e.toString())
        }
    }
}