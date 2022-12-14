package com.umk.catatanku.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.umk.catatanku.preferences.SettingPreferences
import com.umk.catatanku.preferences.UserPreference
import com.umk.catatanku.viewmodel.*

class ViewModelFactory(
    private val userPreference: UserPreference,
    private val application: Application,
    private val settingPreferences: SettingPreferences
) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            userPreference: UserPreference,
            application: Application,
            settingPreferences: SettingPreferences
        ): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(userPreference, application, settingPreferences)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(userPreference, application) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(userPreference) as T
            }
            modelClass.isAssignableFrom(HomepageViewModel::class.java) -> {
                HomepageViewModel(userPreference) as T
            }
            modelClass.isAssignableFrom(NoteAddUpdateViewModel::class.java) -> {
                NoteAddUpdateViewModel(application) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userPreference) as T
            }
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(settingPreferences) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}