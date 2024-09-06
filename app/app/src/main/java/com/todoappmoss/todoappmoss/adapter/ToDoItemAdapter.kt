package com.todoappmoss.todoappmoss.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import at.fhjoanneum.todoappmoss.R
import com.todoappmoss.todoappmoss.data.model.Task

class ToDoItemAdapter(
    private var todoList: List<Task>,
    private val onItemClick: (Task) -> Unit,
    private val onCheckboxChecked: (Task, Boolean) -> Unit
) : RecyclerView.Adapter<ToDoItemAdapter.ToDoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return ToDoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        val todoItem = todoList[position]

        holder.toDoTitle.text = todoItem.title
        holder.toDoDescription.text = todoItem.description ?: ""

        holder.itemCheckbox.isChecked = todoItem.isCompleted


        Log.d("ToDoItemAdapter", "Setting OnCheckedChangeListener for task: ${todoItem.id}")
        holder.itemCheckbox.setOnCheckedChangeListener { _, isChecked ->
            Log.d("ToDoItemAdapter", "Checkbox clicked: $isChecked for task: ${todoItem.id}")
            onCheckboxChecked(todoItem, isChecked)
        }

        holder.itemView.setOnClickListener {
            onItemClick(todoItem)
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun updateData(newTodoList: List<Task>) {
        todoList = newTodoList
        notifyDataSetChanged()
    }

    inner class ToDoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val toDoTitle: TextView = itemView.findViewById(R.id.toDoTitle)
        val toDoDescription: TextView = itemView.findViewById(R.id.toDoDescription)
        val itemCheckbox: CheckBox = itemView.findViewById(R.id.item_checkbox)
    }
}

