package com.umk.catatanku.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.umk.catatanku.database.Note
import com.umk.catatanku.repository.NoteRepository
import com.umk.catatanku.user.User
import com.umk.catatanku.preferences.UserPreference
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreference, private val application: Application) : ViewModel() {
    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    private val mNoteRepository: NoteRepository = NoteRepository(application)
    fun getAllNotes(): LiveData<List<Note>> = mNoteRepository.getAllNotes()
}