package com.umk.catatanku.activities

import android.content.Context
import android.content.Intent
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
import com.umk.catatanku.databinding.ActivityHomepageBinding
import com.umk.catatanku.preferences.SettingPreferences
import com.umk.catatanku.user.User
import com.umk.catatanku.preferences.UserPreference
import com.umk.catatanku.viewmodel.HomepageViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomepageActivity : AppCompatActivity() {

    private lateinit var homepageViewModel: HomepageViewModel
    private lateinit var binding: ActivityHomepageBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        loginSetup()
        intentSignUp()
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
        homepageViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), application, SettingPreferences.getInstance(dataStore))
        )[HomepageViewModel::class.java]

        homepageViewModel.getUser().observe(this) { user ->
            this.user = user
        }
    }

    private fun loginSetup() {
        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()

            when {
                username.isEmpty() -> {
                    binding.edtUsername.error = "required username"
                }
                password.isEmpty() -> {
                    binding.edtPassword.error = "required password"
                }
                username != user.username -> {
                    binding.edtUsername.error = "username tidak cocok"
                }
                password != user.password -> {
                    binding.edtPassword.error = "password tidak cocok"
                }
                else -> {
                    homepageViewModel.login()
                    AlertDialog.Builder(this).apply {
                        setTitle("Login berhasil!")
                        setMessage("Lanjut?")
                        setPositiveButton("Ya") { _, _ ->
                            val intent = Intent(context, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                }
            }
        }
    }

    private fun intentSignUp() {
        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this@HomepageActivity, SignUpActivity::class.java))
        }
    }
}