package com.umk.catatanku.activities

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.umk.catatanku.helper.ViewModelFactory
import com.umk.catatanku.databinding.ActivitySignUpBinding
import com.umk.catatanku.preferences.SettingPreferences
import com.umk.catatanku.user.User
import com.umk.catatanku.preferences.UserPreference
import com.umk.catatanku.viewmodel.SignUpViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        signUpSetup()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        signUpViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), application, SettingPreferences.getInstance(dataStore))
        )[SignUpViewModel::class.java]
    }

    private fun signUpSetup() {
        binding.btnSignup.setOnClickListener {
            val username = binding.edtUsernameSignup.text.toString()
            val email = binding.edtEmailSignup.text.toString()
            val password = binding.edtPasswordSignup.text.toString()
            when {
                username.isEmpty() -> {
                    binding.edtUsernameSignup.error = "required username"
                }
                email.isEmpty() -> {
                    binding.edtEmailSignup.error = "required email"
                }
                password.isEmpty() -> {
                    binding.edtPasswordSignup.error = " required password"
                }
                else -> {
                    signUpViewModel.saveUser(User(username, email, password, false))
                    AlertDialog.Builder(this).apply {
                        setTitle("Registrasi berhasil")
                        setMessage("Lanjut?")
                        setPositiveButton("Ya") { _,_ ->
                            finish()
                        }
                        create()
                        show()
                    }
                }
            }
        }
    }
}