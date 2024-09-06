package com.todoappmoss.todoappmoss.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import ApiClient

class RegisterViewModel : ViewModel() {

    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    private val apiClient = ApiClient()

    fun registerUser(username: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val passwordHash = hashPassword(password)
                val result = apiClient.registerUser(username, email, passwordHash)

                if (result) {
                    _registerResult.postValue(true)
                } else {
                    _registerResult.postValue(false)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                _registerResult.postValue(false)
            }
        }
    }

    private fun hashPassword(password: String): String {
        return password
    }
}