package com.example.myblogapp.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myblogapp.databinding.FragmentNewPostBinding
import com.example.myblogapp.view.interfaces.OnPostSavedListener
import com.example.myblogapp.viewModel.PostViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout

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
        val title = binding.titleTxt.editText?.text
        val content = binding.contentTxt.editText?.text

        validateInput(title, binding.titleTxt, 25, "title")
        validateInput(content, binding.contentTxt, 255, "content")

        binding.titleEvent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                validateInput(s, binding.titleTxt, 25, "title")
                validateInput(binding.contentTxt.editText?.text, binding.contentTxt, 255, "content")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateInput(s, binding.titleTxt, 25, "title")
                validateInput(binding.contentTxt.editText?.text, binding.contentTxt, 255, "content")
            }

            override fun afterTextChanged(s: Editable?) {
                validateInput(s, binding.titleTxt, 25, "title")
                validateInput(binding.contentTxt.editText?.text, binding.contentTxt, 255, "content")
            }
        })

        binding.contentEvent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                validateInput(s, binding.contentTxt, 255, "content")
                validateInput(binding.titleTxt.editText?.text, binding.titleTxt, 25, "title")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateInput(s, binding.contentTxt, 255, "content")
                validateInput(binding.titleTxt.editText?.text, binding.titleTxt, 25, "title")
            }

            override fun afterTextChanged(s: Editable?) {
                validateInput(s, binding.contentTxt, 255, "content")
                validateInput(binding.titleTxt.editText?.text, binding.titleTxt, 25, "title")
            }
        })

        binding.contentEvent.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener actionId == EditorInfo.IME_ACTION_DONE
        }

        binding.saveBtn.setOnClickListener {
            saveBtn()
        }
    }

    private fun validateInput(
        s: CharSequence?,
        inputEvent: TextInputLayout,
        maxLength: Int,
        inputType: String
    ) {
        if ((s?.length ?: 0) > maxLength) {
            inputEvent.error = "The $inputType must be no longer than $maxLength characters"
            binding.saveBtn.isEnabled = false
        } else if (s?.isEmpty() == true || s?.trim()?.length == 0) {
            inputEvent.error = "The $inputType must be not empty"
            binding.saveBtn.isEnabled = false
        } else {
            inputEvent.error = null
            binding.saveBtn.isEnabled = true
        }
    }

    private fun saveBtn() {
        if (isNetworkAvailable(requireContext())) {
            val title = binding.titleTxt.editText?.text.toString()
            val content = binding.contentTxt.editText?.text.toString()
            onPostSavedListener?.onPostSaved(title, content)
            dismiss()
        } else {
            binding.saveBtn.isEnabled = false
            Toast.makeText(requireContext(), "No internet connection !!!", Toast.LENGTH_LONG).show()
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
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