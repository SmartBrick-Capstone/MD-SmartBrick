package com.github.emmpann.smartbrick.feature.verification

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.databinding.FragmentVerificationBinding
import com.github.emmpann.smartbrick.databinding.LayoutLoadingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationFragment : Fragment() {

    private val viewModel: VerificationViewModel by viewModels()
    private lateinit var binding: FragmentVerificationBinding
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentVerificationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupClickListener()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.getEmail()
        viewModel.isUserVerify.observe(viewLifecycleOwner) {
            if (it) {
                // user verified
                binding.layoutUnverifiedAccount.visibility = View.GONE
                binding.tvVerifiedAccount.visibility = View.VISIBLE
            }
        }

        viewModel.sendOtpResponse.observe(viewLifecycleOwner) {

            when (it) {
                is ResultApi.Success -> {
                    with (binding) {
                        tvPleaseEnterCode.visibility = View.VISIBLE
                        tvDidntReceiveCode.visibility = View.VISIBLE
                        btnResendCode.visibility = View.VISIBLE
                        btnSendOtp.visibility = View.GONE
                    }
                    showLoading(false)
                }

                is ResultApi.Error -> {
                    showErrorDialog(it.error)
                    showLoading(false)
                }

                is ResultApi.Loading -> {
                    showLoading(true)
                }
            }
        }

        viewModel.verifyOtpResponse.observe(viewLifecycleOwner) {
            when (it) {
                is ResultApi.Success -> {
                    viewModel.setUserVerified(true)
                    showLoading(false)
                    showSuccessDialog(it.data.message)
                }

                is ResultApi.Error -> {
                    showLoading(false)
                    showErrorDialog(it.error)
                }

                is ResultApi.Loading -> {
                    showLoading(true)
                }
            }
        }
    }

    private fun setupClickListener() {
        with(binding) {

            btnVerify.setOnClickListener {
                val otp = "${edNumber1.text}${edNumber2.text}${edNumber3.text}${edNumber4.text}"
                viewModel.verifyOtp(viewModel.currentEmail.value.toString(), otp)
            }

            btnSendOtp.setOnClickListener {
//                Log.d("email verif", viewModel.currentEmail.value.toString())
                viewModel.sendOtp(viewModel.currentEmail.value.toString())
            }

            btnResendCode.setOnClickListener {
                viewModel.startCountDownResendButtonTimer()
                viewModel.sendOtp(viewModel.currentEmail.value.toString())
            }
        }
    }

    private fun setupView() {
        binding.apply {
            btnBack.setOnClickListener { findNavController().popBackStack() }
            edNumber1.addTextChangedListener(nextEdFocus(edNumber1, edNumber2))
            edNumber2.addTextChangedListener(nextEdFocus(edNumber2, edNumber3))
            edNumber3.addTextChangedListener(nextEdFocus(edNumber3, edNumber4))
            edNumber4.addTextChangedListener(nextEdFocus(edNumber4, null))

            viewModel.eventCountDownFinish.observe(viewLifecycleOwner) {
                btnResendCode.isEnabled = it
                if (it) {
                    btnResendCode.text = getString(R.string.resend_code)
                }
            }

            viewModel.currentTimeString.observe(viewLifecycleOwner) {
                btnResendCode.text = it
            }
        }
    }

    private fun nextEdFocus(editText: EditText, nextEditText: EditText?): TextWatcher {
        return object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (editText.text.toString().length == 1) {
                    nextEditText?.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (::dialog.isInitialized.not()) {
            dialog = Dialog(requireContext(), R.style.Dialog_Loading)
            val dialogBinding = LayoutLoadingBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.window?.apply {
                setLayout(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                )
            }
        }

        if (isLoading) dialog.show() else dialog.hide()
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