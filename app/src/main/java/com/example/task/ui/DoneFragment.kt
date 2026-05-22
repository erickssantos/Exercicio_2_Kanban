package com.example.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.R
import com.example.task.data.model.Status
import com.example.task.data.model.Task
import com.example.task.databinding.FragmentDoneBinding
import com.example.task.ui.adapter.TaskAdapter
import com.example.task.util.FirebaseHelper
import com.example.task.util.showBottonSheet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class DoneFragment : Fragment() {

    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter
    private val viewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerViewModel()
        initRecyclerViewTask()
        getTask()
    }

    private fun observerViewModel(){
        viewModel.taskUpdate.observe(viewLifecycleOwner){ updateTask ->
            if(updateTask.status == Status.DONE){
                // Armazena a lista atual do adaptador
                val oldList = taskAdapter.currentList

                // Gera uma nova lista a partir da lista antiga já com a tarefa atualiazada
                val newList = oldList.toMutableList().apply {
                    find{ it.id == updateTask.id}?.description = updateTask.description
                }

                val position = newList.indexOfFirst { it.id == updateTask.id }

                //Envia a lista atualiazada para o adapter
                taskAdapter.submitList(newList)

                // Atualiza a tarefa pela posição do adapter
                taskAdapter.notifyItemChanged(position)
            }
        }
    }

    private fun initRecyclerViewTask() {
        taskAdapter = TaskAdapter(requireContext()) {task, option -> optionSelected(task,option)}

        with(binding.RecyclerViewTask){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }
    }

    private fun optionSelected(task:Task, option:Int){
        when (option){
            TaskAdapter.SELECT_REMOVER -> {
                showBottonSheet(titleDialog = R.string.text_title_dialog_delete,
                    message = getString(R.string.text_message_dialog_delete),
                    titleButton = R.string.text_button_dialog_confirm,
                    onClick = {
                        deleteTask(task)
                    }
                )
            }
            TaskAdapter.SELECT_EDIT -> {
                val action = HomeFragmentDirections.actionHomeFragmentToFormTaskFragment(task)
                findNavController().navigate(action)
            }

            TaskAdapter.SELECT_DETAILS -> {
                Toast.makeText(requireContext(),"Detalhes ${task.description}", Toast.LENGTH_SHORT).show()
            }

            TaskAdapter.SELECT_BACK -> {
                task.status = Status.DOING
                updateTask(task)
            }
        }
    }

    private fun getTask() {
        FirebaseHelper.getDatabase()
            .child("task")
            .child(FirebaseHelper.getIdUser())
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    val taskList = mutableListOf<Task>()

                    for (ds in p0.children){
                        val task = ds.getValue(Task::class.java) as Task 
                        if(task.status == Status.DONE) {
                            taskList.add(task)
                        }
                    }
                    binding.progressBar.isVisible = false
                    listEmpty(taskList)
                    taskList.reverse()
                    taskAdapter.submitList(taskList)
                }
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(requireContext(), R.string.error_generic, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun listEmpty(taskList: List<Task>){
        binding.textInfo.text = if (taskList.isEmpty()){
            getString(R.string.text_list_task_empty)
        }else{
            ""
        }
    }

    private fun deleteTask(task: Task){
        FirebaseHelper.getDatabase()
            .child("task")
            .child(FirebaseHelper.getIdUser())
            .child(task.id)
            .removeValue().addOnCompleteListener { result ->
                if (result.isSuccessful){
                    Toast.makeText(requireContext(), R.string.text_delete_sucess_task, Toast.LENGTH_SHORT).show()

                    val oldList = taskAdapter.currentList
                    val newList = oldList.toMutableList().apply { remove(task) }
                    taskAdapter.submitList(newList)
                }else{
                    Toast.makeText(requireContext(), R.string.error_generic, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateTask(task:Task){
        FirebaseHelper.getDatabase()
            .child("task")
            .child(FirebaseHelper.getIdUser())
            .child(task.id)
            .setValue(task).addOnCompleteListener { result ->
                if (result.isSuccessful){
                    Toast.makeText(requireContext(), R.string.text_save_sucess_form_task_fragment, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), R.string.error_generic, Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}