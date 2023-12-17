package com.github.emmpann.smartbrick.feature.verification

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.databinding.FragmentVerificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationFragment : Fragment() {

    private val viewModel: VerificationViewModel by viewModels()
    private lateinit var binding: FragmentVerificationBinding

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
    }

    private fun setupView() {
        binding.apply {
            btnBack.setOnClickListener { findNavController().popBackStack() }
            edNumber1.addTextChangedListener(nextEdFocus(edNumber1, edNumber2))
            edNumber2.addTextChangedListener(nextEdFocus(edNumber2, edNumber3))
            edNumber3.addTextChangedListener(nextEdFocus(edNumber3, edNumber4))
            edNumber4.addTextChangedListener(nextEdFocus(edNumber4, null))

            btnResendCode.setOnClickListener {
                viewModel.startCountDownResendButtonTimer()
            }

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
}