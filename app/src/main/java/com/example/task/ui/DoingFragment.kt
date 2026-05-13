package com.example.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.R
import com.example.task.data.model.Status
import com.example.task.data.model.Task
import com.example.task.databinding.FragmentDoingBinding
import com.example.task.databinding.FragmentDoneBinding
import com.example.task.ui.adapter.TaskAdapter


class DoingFragment : Fragment() {

    private var _binding: FragmentDoingBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViewTask(getTask())
    }

    private fun initRecyclerViewTask(taskList: List<Task>) {

        taskAdapter = TaskAdapter(requireContext(),taskList)
        binding.RecyclerViewTask.layoutManager = LinearLayoutManager(requireContext())
        binding.RecyclerViewTask.setHasFixedSize(true)

        binding.RecyclerViewTask.adapter = taskAdapter
    }

    private fun getTask() = listOf(
        Task("0","Começar programação em bloco", Status.DOING),
        Task("2", "Enviar para o GitHub", Status.DOING),
        Task("1","Revisar código", Status.DOING),

    )
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}