package com.github.emmpann.smartbrick.feature.helpsupport

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.databinding.FragmentHelpSupportBinding

class HelpSupportFragment : Fragment() {

    private lateinit var binding: FragmentHelpSupportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHelpSupportBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListener()
    }

    private fun setupClickListener() {
        with(binding) {

            btnBack.setOnClickListener { findNavController().popBackStack() }

            btnSendEmail.setOnClickListener {
                if (etSubject.text.toString().isNotEmpty() && etMessageDetail.text.toString()
                        .isNotEmpty()
                ) {
                    errorForm.visibility = View.GONE
                    composeNewEmail(
                        getString(R.string.app_email),
                        etSubject.text.toString(),
                        etMessageDetail.text.toString()
                    )
                } else {
                    errorForm.visibility = View.VISIBLE
                }
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun composeNewEmail(emailAddress: String, subject: String, message: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, emailAddress)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.no_email_app_installed),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}