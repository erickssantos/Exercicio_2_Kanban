package com.example.task.util

import android.app.Dialog
import android.os.Message
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.task.R
import com.example.task.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.initToolbar(toolbar: Toolbar){
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title=""
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }
}

fun Fragment.showBottonSheet(
    titleDialog: Int? = null,
    titleButton: Int? = null,
    message: Int,
    onClick: () -> Unit ={}
){
    val bottomSheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialog)
    val binding: BottomSheetBinding =
        BottomSheetBinding.inflate(layoutInflater, null, false)

    binding.textviewTitle.text = getText(titleDialog ?: R.string.text_title_warning )
    binding.textviewMessage.text = getText(message)
    binding.buttonOk.text = getText(titleButton ?: R.string.text_button_wargning)
    binding.buttonOk.setOnClickListener {
        onClick()
        bottomSheetDialog
    }

    bottomSheetDialog.setContentView(binding.root)
    bottomSheetDialog.show()
}
