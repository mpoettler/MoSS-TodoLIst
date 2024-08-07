package com.example.todoappmoss.bloc


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoappmoss.data.model.ToDoItem
import com.example.todoappmoss.data.Service.remote.repository.ToDoRepository
import java.io.IOException

class ToDoBloc : ViewModel() {
    private val repository = ToDoRepository()

    private val _toDoItems = MutableLiveData<List<ToDoItem>?>()
    val toDoItems: MutableLiveData<List<ToDoItem>?> get() = _toDoItems

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchToDoItems() {
        try {
            val items = repository.getToDoItems()
            if (items != null) {
                _toDoItems.postValue(items)
            } else {
                _error.postValue("Failed to fetch data")
            }
        } catch (e: IOException) {
            _error.postValue(e.message)
        }
    }

    // Implementiere Methoden für createToDoItem, updateToDoItem, deleteToDoItem
}
