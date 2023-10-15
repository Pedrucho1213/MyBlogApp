package com.example.myblogapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import com.example.myblogapp.databinding.FragmentNewPostBinding
import com.example.myblogapp.view.interfaces.OnPostSavedListener
import com.example.myblogapp.viewModel.PostViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewPostFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNewPostBinding
    private lateinit var viewModel: PostViewModel
    private var onPostSavedListener: OnPostSavedListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        onPostSavedListener = activity as? OnPostSavedListener
        inputValidations()
        viewModel = ViewModelProvider(this)[PostViewModel::class.java]
    }

    private fun inputValidations(){
        binding.titleEvent.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener actionId == EditorInfo.IME_ACTION_NEXT
        }

        binding.contentEvent.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener actionId == EditorInfo.IME_ACTION_DONE
        }

        binding.saveBtn.setOnClickListener {
            saveBtn()
        }
    }
    private fun saveBtn() {
        val title = binding.titleTxt.editText?.text.toString()
        val content = binding.contentTxt.editText?.text.toString()
        onPostSavedListener?.onPostSaved(title, content)
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewPostBinding.inflate(inflater, container, false)
        return binding.root
    }
}