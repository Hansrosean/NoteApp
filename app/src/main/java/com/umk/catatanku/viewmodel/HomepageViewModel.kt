package com.umk.catatanku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.umk.catatanku.user.User
import com.umk.catatanku.preferences.UserPreference
import kotlinx.coroutines.launch

class HomepageViewModel(private val pref: UserPreference) : ViewModel()  {
    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }
}