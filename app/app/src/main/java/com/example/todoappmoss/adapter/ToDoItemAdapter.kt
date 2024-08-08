package com.example.todoappmoss.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappmoss.R
import com.example.todoappmoss.data.model.ToDoItem

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
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val tvDeadline: TextView = itemView.findViewById(R.id.tvDeadline)

        fun bind(toDoItem: ToDoItem) {
            tvTitle.text = toDoItem.title
            tvDescription.text = toDoItem.description
            tvDeadline.text = toDoItem.deadline
        }
    }
}


