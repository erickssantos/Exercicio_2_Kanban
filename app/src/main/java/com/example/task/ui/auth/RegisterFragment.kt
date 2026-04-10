package com.example.task.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.task.R
import com.example.task.databinding.FragmentRegisterBinding
import com.example.task.util.initToolbar
import com.example.task.util.showBottonSheet


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListener()
    }

    private fun initListener(){
        binding.btnCriarConta.setOnClickListener {
            validateData()
        }
    }

    private fun validateData(){
        val email = binding.editEmail.text.toString().trim()
        val senha = binding.editSenha.text.toString().trim()

        if (email.isNotBlank()){
            if (senha.isNotBlank()){
                Toast.makeText(requireContext(), "Tudo OK!", Toast.LENGTH_SHORT).show()
            }else{
                showBottonSheet(message = getString(R.string.password_empty_register_fragment))
            }
        }else{
            showBottonSheet(message = getString(R.string.email_empty_register_fragment))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}