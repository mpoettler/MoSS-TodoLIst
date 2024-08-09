package com.example.todoappmoss.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappmoss.data.model.ToDoItem
import com.example.todolistapp.R

class ToDoItemAdapter(private val toDoItems: List<ToDoItem>) :
    RecyclerView.Adapter<ToDoItemAdapter.ToDoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return ToDoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        val toDoItem = toDoItems[position]
        holder.bind(toDoItem)
    }

    override fun getItemCount() = toDoItems.size

    inner class ToDoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val toDoTitle: TextView = itemView.findViewById(R.id.toDoTitle)
        val itemText: TextView = itemView.findViewById(R.id.toDoDescription)
        val itemCheckbox: CheckBox = itemView.findViewById(R.id.item_checkbox)

        fun bind(toDoItem: ToDoItem) {
            toDoTitle.text = toDoItem.title
            itemText.text = toDoItem.description
            itemCheckbox.text = toDoItem.deadline
        }
    }
}


