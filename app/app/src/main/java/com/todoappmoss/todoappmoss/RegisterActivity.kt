package com.todoappmoss.todoappmoss.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import at.fhjoanneum.todoappmoss.databinding.ActivityRegisterBinding
import at.fhjoanneum.todoappmoss.ui.login.LoginActivity
import com.todoappmoss.todoappmoss.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val email = binding.email
        val password = binding.password
        val registerButton = binding.registerButton
        val cancelButton = binding.cancelButton

        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        registerViewModel.registerResult.observe(this, Observer { result ->
            if (result) {
                Toast.makeText(this, "Registrierung erfolgreich!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Registrierung fehlgeschlagen", Toast.LENGTH_LONG).show()
            }
        })

        registerButton.setOnClickListener {
            val usernameText = username.text.toString()
            val emailText = email.text.toString()
            val passwordText = password.text.toString()

            Log.d("RegisterActivity", "Trying to register user: $usernameText, $emailText")

            if (usernameText.isNotBlank() && emailText.isNotBlank() && passwordText.isNotBlank()) {
                registerViewModel.registerUser(usernameText, emailText, passwordText)
            } else {
                Toast.makeText(this, "Bitte füllen Sie alle Felder aus.", Toast.LENGTH_SHORT).show()
            }
        }

        cancelButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
