package com.github.emmpann.smartbrick.feature.detail

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.core.util.CameraUtil.CAMERA_BUNDLE_KEY
import com.github.emmpann.smartbrick.core.util.CameraUtil.CAMERA_REQUEST_KEY
import com.github.emmpann.smartbrick.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setClickListener()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.setCurrentImage(DetailFragmentArgs.fromBundle(arguments as Bundle).imageUri.toUri())

        viewModel.currentImageUri.observe(viewLifecycleOwner) {
            Log.d("imageUri detail", it.toString())
            showImage(it)
        }
    }

    private fun setClickListener() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun showImage(imageUri: Uri) {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    requireContext().contentResolver,
                    imageUri
                )
            )
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
        }
        binding.ivDetailPlasticResult.setImageBitmap(bitmap)
    }
}