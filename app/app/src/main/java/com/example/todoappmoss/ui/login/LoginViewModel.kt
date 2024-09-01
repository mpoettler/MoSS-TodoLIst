package com.example.todoappmoss.ui.login

import ApiClient
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.todoappmoss.data.LoginRepository
import com.example.todoappmoss.data.Result

import com.example.todolistapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

        private val _loginResult = MutableLiveData<LoginResult>()
        val loginResult: LiveData<LoginResult> = _loginResult


    }

    fun register(username: String, email: String, password: String) {
        try {
            val apiClient = ApiClient()
            val success = apiClient.register(username, email, password)

            if (success) {
                _loginResult.value = LoginResult(success = LoggedInUserView(displayName = username))
            } else {
                _loginResult.value = LoginResult(error = 1)
            }
        } catch (e: IOException) {
            _loginResult.value = LoginResult(error = 1)
        }
    }

    fun login(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("LoginViewModel", "Starting login with username: $username")
                val apiClient = ApiClient()
                val success = apiClient.login(username, password)


                withContext(Dispatchers.Main) {
                    if (success) {
                        Log.d("LoginViewModel", "Login successful")
                        _loginResult.value = LoginResult(success = LoggedInUserView(displayName = username))
                    } else {
                        Log.e("LoginViewModel", "Login failed")
                        _loginResult.value = LoginResult(error = R.string.login_failed)
                    }
                }
            } catch (e: IOException) {
                Log.e("LoginViewModel", "Network error during login", e)
                withContext(Dispatchers.Main) {
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }
            }
        }
    }


    fun loginDataChanged(username: String, password: String) {

        _loginForm.value = LoginFormState(isDataValid = true)
        /*
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }

         */
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}