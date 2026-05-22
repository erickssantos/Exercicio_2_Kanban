package com.example.task.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.task.R
import com.example.task.databinding.FragmentRecoverAccountBinding
import com.example.task.util.initToolbar
import com.example.task.util.showBottonSheet
import com.google.firebase.auth.FirebaseAuth

class RecoverAccountFragment : Fragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        auth = FirebaseAuth.getInstance()

        iniListener()
    }

    private fun iniListener() {
        binding.btnRecover.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.editEmail.text.toString().trim()
        if(email.isNotBlank()) {
            binding.progressBar.isVisible = true
            recoverAccountUser(email)
        } else {
            showBottonSheet(message = getString(R.string.email_empty))
        }
    }

    private fun recoverAccountUser(email: String) {
        try {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    binding.progressBar.isVisible = false
                    if (task.isSuccessful) {
                        showBottonSheet(message = getString(R.string.text_message_recover_account_fragment))
                    } else {
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