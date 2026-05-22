package com.example.task.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.databinding.FragmentRegisterBinding
import com.example.task.util.initToolbar
import com.example.task.util.showBottonSheet
import com.google.firebase.auth.FirebaseAuth


class RegisterFragment : Fragment() {


    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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

    private fun initListener() {
        binding.btnCriarConta.setOnClickListener {
            validateData()
        }
    }


    private fun validateData() {
        val email = binding.editEmail.text.toString().trim()
        val senha = binding.editSenha.text.toString().trim()
        if(email.isNotBlank()) {
            if(senha.isNotBlank()) {
                binding.progressBar.isVisible = true
                registerUser(email, senha)
                Toast.makeText(requireContext(), "Tudo OK!", Toast.LENGTH_SHORT).show()
            } else {
                showBottonSheet(message = getString(R.string.password_empty_register_fragment))
            }
        } else {
            showBottonSheet(message = getString(R.string.email_empty_register_fragment))
        }
    }

    private fun registerUser(email:String, password: String) {
        try {
            // instanciando a variavel q representa o serviço de autenticação
            val auth = FirebaseAuth.getInstance()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        // encaminha para a tela home
                        findNavController().navigate(R.id.action_global_homeFragment)
                    } else {
                        binding.progressBar.isVisible = true
                        Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}