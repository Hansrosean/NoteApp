package com.umk.catatanku.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.umk.catatanku.R
import com.umk.catatanku.databinding.ActivityProfileBinding
import com.umk.catatanku.helper.ViewModelFactory
import com.umk.catatanku.preferences.SettingPreferences
import com.umk.catatanku.preferences.UserPreference
import com.umk.catatanku.viewmodel.ProfileViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = resources.getString(R.string.my_profile)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewModel()
    }

    private fun setupViewModel() {
        profileViewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                UserPreference.getInstance(dataStore),
                application,
                SettingPreferences.getInstance(dataStore)
            )
        )[ProfileViewModel::class.java]

        profileViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                val setUsername = "My name : " + user.username
                binding.tvProfileName.text = setUsername

                val setEmail = "My email : " + user.email
                binding.tvProfileEmail.text = setEmail
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}