package com.github.emmpann.smartbrick.feature.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.databinding.FragmentLoginBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.loginResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ResultApi.Success -> {
                    viewModel.setSession(response.data.loginResult)
                    btnVisibility(true)
                }

                is ResultApi.Error -> {
                    btnVisibility(true)
                    showErrorDialog(response.error)
                }

                is ResultApi.Loading -> {
                    btnVisibility(false)
                }
            }
        }
    }

    private fun setupAction() {
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            viewModel.login(email, password)
        }
    }

    private fun btnVisibility(isShow: Boolean) {
        binding.progressBar.visibility = if (isShow) View.GONE else View.VISIBLE
        binding.btnLogin.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun showErrorDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.error))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->

            }.show()
    }
}