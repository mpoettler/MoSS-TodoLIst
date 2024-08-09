package com.example.todoappmoss.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappmoss.data.model.ToDoItem
import com.example.todolistapp.R

class ToDoItemAdapter(private val todoList: List<ToDoItem>) : RecyclerView.Adapter<ToDoItemAdapter.ToDoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return ToDoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        val todoItem = todoList[position]
        holder.toDoTitle.text = todoItem.title.first().toString()
        holder.itemText.text = todoItem.title
        holder.itemCheckbox.isChecked = todoItem.isCompleted
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    inner class ToDoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val toDoTitle: TextView = itemView.findViewById(R.id.toDoTitle)
        val itemText: TextView = itemView.findViewById(R.id.todoEditText)
        val itemCheckbox: CheckBox = itemView.findViewById(R.id.item_checkbox)
    }
}

data class ToDoItem(val title: String, val isDone: Boolean)



