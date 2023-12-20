package com.github.emmpann.smartbrick.feature.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.databinding.FragmentRegisterBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnSignup.setOnClickListener {
            viewModel.register(
                binding.edName.text.toString(),
                binding.edEmail.text.toString(),
                binding.edPassword.text.toString()
            )
        }
    }

    private fun setupObserver() {
        viewModel.registerResponse.observe(viewLifecycleOwner) {
            when (it) {
                is ResultApi.Success -> {
                    btnVisibility(true)
                    showSuccessDialog(it.data.message)
                    with(binding) {
                        edName.text?.clear()
                        edEmail.text?.clear()
                        edPassword.text?.clear()
                    }
                }

                is ResultApi.Error -> {
                    btnVisibility(true)
                    showErrorDialog(it.error)
                }

                is ResultApi.Loading -> {
                    btnVisibility(false)
                }
            }
        }
    }

    private fun btnVisibility(isShow: Boolean) {
        binding.progressBar.visibility = if (isShow) View.GONE else View.VISIBLE
        binding.btnSignup.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun showSuccessDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.successfully))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->

            }.show()
    }

    private fun showErrorDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.error))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->

            }.show()
    }
}