package com.umk.catatanku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.umk.catatanku.user.User
import com.umk.catatanku.preferences.UserPreference

class ProfileViewModel(private val pref: UserPreference): ViewModel() {
    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }
}