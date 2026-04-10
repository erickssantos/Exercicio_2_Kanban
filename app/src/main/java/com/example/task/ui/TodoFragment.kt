package com.example.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.R
import com.example.task.data.model.Status
import com.example.task.data.model.Task
import com.example.task.databinding.FragmentDoneBinding
import com.example.task.databinding.FragmentTodoBinding
import com.example.task.ui.adapter.TaskAdapter


class TodoFragment : Fragment() {

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()

        initRecyclerViewTask(getTask())
    }

    private fun initListeners(){
        binding.floatingActionButton2.setOnClickListener {
            findNavController().navigate((R.id.action_homeFragment_to_formTaskFragment))
        }
    }

    private fun initRecyclerViewTask(taskList: List<Task>) {

        taskAdapter = TaskAdapter(taskList)
        binding.RecyclerViewTask.layoutManager = LinearLayoutManager(requireContext())
        binding.RecyclerViewTask.setHasFixedSize(true)

        binding.RecyclerViewTask.adapter = taskAdapter
    }

    private fun getTask() = listOf(
        Task("0","Criar nova tela do app", Status.TODO),
        Task("1", "Validar informações na tela de login", Status.TODO),
        Task("3", "Salvar token localmente", Status.TODO),
        Task("2","Criar funcionalidade de logout no app", Status.TODO)
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

