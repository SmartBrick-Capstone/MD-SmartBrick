package com.github.emmpann.smartbrick.feature.scan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.databinding.FragmentScanBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanFragment : Fragment() {

    private lateinit var binding: FragmentScanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentScanBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setEventClickListener()
    }

    private fun setEventClickListener() {
        binding.btnCamera.setOnClickListener {
            findNavController().navigate(R.id.action_scanFragment_to_cameraFragment)
        }
    }
}