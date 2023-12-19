package com.github.emmpann.smartbrick.feature.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.databinding.FragmentProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setEventClickListener()
    }

    private fun setupObserver() {
        Glide.with(binding.root)
            .load("https://gravatar.com/avatar/639d5f5a7ae8bc25f02b30d6059800cf?s=400&d=retro&r=x")
            .into(binding.ivProfile)
        viewModel.email.observe(viewLifecycleOwner) {
            binding.tvEmail.text = it
        }

        viewModel.name.observe(viewLifecycleOwner) {
            binding.tvUsername.text = it
        }
    }

    private fun setEventClickListener() {
        binding.btnHistory.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_historyFragment)
        }
        binding.btnVerification.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_verificationFragment)
        }
        binding.btnHelpSupport.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_helpSupportFragment)
        }
        binding.btnAboutApp.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_aboutAppFragment)
        }
        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.logout))
            .setMessage(getString(R.string.are_you_sure_you_want_to_log_out_of_your_account))
            .setNegativeButton(getString(R.string.no)) { dialog, which ->

            }
            .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                viewModel.clearSession()
            }.show()

    }
}