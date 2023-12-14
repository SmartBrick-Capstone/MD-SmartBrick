package com.github.emmpann.smartbrick.feature.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
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
                    Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT).show()
                }

                is ResultApi.Error -> {
                    btnVisibility(true)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
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
}