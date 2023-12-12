package com.github.emmpann.smartbrick.feature.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.databinding.FragmentProfileBinding
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

        setEventClickListener()
    }

    private fun setEventClickListener() {
        binding.btnHistory.setOnClickListener {
            TODO("not implemented")
        }
        binding.btnVerification.setOnClickListener {
            TODO("not implemented")
        }
        binding.btnHelpSupport.setOnClickListener {
            TODO("not implemented")
        }
        binding.btnAboutApp.setOnClickListener {
            TODO("not implemented")
        }
        binding.btnAboutApp.setOnClickListener {
            TODO("not implemented")
        }
        binding.btnLogout.setOnClickListener {
            viewModel.clearSession()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }
}