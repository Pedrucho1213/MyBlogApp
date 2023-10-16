package com.example.myblogapp.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

    private fun inputValidations() {
        binding.titleEvent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // TODO
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // TODO
            }

            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) > 15) {
                    binding.titleEvent.error = "The title must be no longer than 15 characters"
                    binding.saveBtn.isEnabled = false
                } else {
                    binding.titleEvent.error = null
                    binding.saveBtn.isEnabled = true
                }
            }
        })

        binding.contentEvent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // TODO
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // TODO
            }

            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) > 255) {
                    binding.contentEvent.error = "The content must be no longer than 255 characters"
                    binding.saveBtn.isEnabled = false
                } else {
                    binding.contentEvent.error = null
                    binding.saveBtn.isEnabled = true
                }
            }
        })

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