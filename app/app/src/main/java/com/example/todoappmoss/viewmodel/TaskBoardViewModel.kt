package com.example.todoappmoss.ui.taskboard

import ApiClient
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoappmoss.data.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class TaskBoardViewModel : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    private val apiClient = ApiClient()

    fun loadTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val items = apiClient.getTodoItems()
                _tasks.postValue(items)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun loadSingleTaskById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val item = apiClient.getTodoItemById(id)
                item?.let {
                    val currentList = _tasks.value.orEmpty().toMutableList()
                    currentList.add(it)
                    _tasks.postValue(currentList)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
