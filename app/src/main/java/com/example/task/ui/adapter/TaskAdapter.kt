package com.example.task.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.NavType
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.data.model.Status
import com.example.task.data.model.Task
import com.example.task.databinding.ItemTaskBinding
import com.google.android.material.animation.Positioning

class TaskAdapter(
    private val context: Context,
    private val taskSelected: (Task,Int) -> Unit
): ListAdapter<Task, TaskAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object{
        val SELECT_BACK: Int = 1
        val SELECT_REMOVER: Int = 2
        val SELECT_EDIT: Int = 3
        val SELECT_DETAILS: Int = 4
        val SELECT_NEXT: Int = 5

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>(){
            override fun areItemsTheSame(
                oldItem: Task,
                newItem: Task
            ): Boolean {
                return oldItem.id == newItem.id && oldItem.description == newItem.description
            }

            override fun areContentsTheSame(
                oldItem: Task,
                newItem: Task
            ): Boolean {
                return oldItem == newItem && oldItem.description == newItem.description
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = getItem(position)
        holder.binding.textDescription.text = task.description

        setIndicators(task,holder)
    }

    private fun setIndicators(task: Task, holder: MyViewHolder){
        when (task.status){
            Status.TODO -> {
                holder.binding.buttonBack.isVisible = false
                holder.binding.buttonFoward.setOnClickListener { taskSelected(task, SELECT_NEXT) }
            }
            Status.DOING ->{
                holder.binding.buttonBack.setColorFilter(ContextCompat.getColor(context, R.color.color_status_todo))
                holder.binding.buttonFoward.setColorFilter(ContextCompat.getColor(context, R.color.color_status_done))
                holder.binding.buttonFoward.setOnClickListener { taskSelected(task, SELECT_NEXT) }
                holder.binding.buttonBack.setOnClickListener { taskSelected(task, SELECT_BACK) }
            }
            Status.DONE -> {
                holder.binding.buttonFoward.isVisible = false
                holder.binding.buttonBack.setOnClickListener { taskSelected(task,SELECT_BACK) }
            }
        }

        holder.binding.buttonDelete.setOnClickListener { taskSelected(task, SELECT_REMOVER) }
        holder.binding.buttonEditar.setOnClickListener { taskSelected(task, SELECT_EDIT) }
        holder.binding.buttonDetails.setOnClickListener { taskSelected(task, SELECT_DETAILS) }
    }
    inner class MyViewHolder(val binding :  ItemTaskBinding): RecyclerView.ViewHolder(binding.root){

    }

}