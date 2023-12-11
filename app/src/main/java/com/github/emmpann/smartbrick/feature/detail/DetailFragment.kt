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
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.core.util.CameraUtil.CAMERA_BUNDLE_KEY
import com.github.emmpann.smartbrick.core.util.CameraUtil.CAMERA_REQUEST_KEY
import com.github.emmpann.smartbrick.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("DetailFragment", "opened detail page with: current image $currentImageUri")

        navigateToCamera()
    }

    private fun navigateToCamera() {
        setFragmentResultListener(CAMERA_REQUEST_KEY) { _, bundle ->
            val result = bundle.getString(CAMERA_BUNDLE_KEY)?.toUri()
            result?.let {
                currentImageUri = it
                showImage(it)
            } ?: run {
//                showToast("No image captured")
            }
        }
        if (currentImageUri == null) {
            findNavController().navigate(R.id.action_detailFragment_to_cameraFragment)
        }
    }

    private fun showImage(imageUri: Uri) {
        currentImageUri = imageUri
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
        binding.ivDetailPlastic.setImageBitmap(bitmap)
    }
}