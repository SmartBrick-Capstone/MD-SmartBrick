package com.github.emmpann.smartbrick.feature.helpsupport

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private fun composeNewEmail(emailAddress: String, subject: String, message: String) {
        val intent = Intent(
            Intent.ACTION_SENDTO,
            Uri.parse("mailto:?subject=$subject&to=$emailAddress&body=$message")
        )
        startActivity(Intent.createChooser(intent, "Send feedback"))
    }
}