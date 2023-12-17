package com.github.emmpann.smartbrick.feature.camera

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.core.util.CameraUtil.CAMERA_BUNDLE_KEY
import com.github.emmpann.smartbrick.core.util.CameraUtil.CAMERA_REQUEST_KEY
import com.github.emmpann.smartbrick.core.util.ImageUtil.createCustomTempFile
import com.github.emmpann.smartbrick.databinding.FragmentCameraBinding
import com.github.emmpann.smartbrick.databinding.LayoutLoadingBinding
import com.github.emmpann.smartbrick.feature.detail.DetailFragmentArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraFragment : Fragment() {

    private val viewModel: CameraViewModel by viewModels()
    private lateinit var binding: FragmentCameraBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null
    private lateinit var dialog: Dialog
    private var isFlashOn: Boolean = true
    private lateinit var cameraControl: CameraControl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startCamera()
        setEventClickListener()
    }

    private fun setEventClickListener() {
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.btnCameraShutter.setOnClickListener { takePhoto() }
        binding.btnInfo.setOnClickListener { }
        binding.btnFlash.setOnClickListener {
            viewModel.setFlashLight()
        }

        viewModel.isFlashOn.observe(viewLifecycleOwner) {
            imageCapture?.flashMode = if (it) {
                binding.btnFlash.setImageResource(R.drawable.ic_flash_on)
                ImageCapture.FLASH_MODE_ON
            } else {
                binding.btnFlash.setImageResource(R.drawable.ic_flash)
                ImageCapture.FLASH_MODE_OFF
            }

            Log.d("CameraX", "Flash Mode: ${imageCapture?.flashMode}")
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG)
                    .show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private fun startCamera() {
        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.preview.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {

            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = createCustomTempFile(requireContext())

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Toast.makeText(
                        requireContext(),
                        "Berhasil mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()

                    val toDetailFragment =
                        CameraFragmentDirections.actionCameraFragmentToDetailFragment()
                    toDetailFragment.imageUri = output.savedUri.toString()
                    findNavController().navigate(toDetailFragment)
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        requireContext(),
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    private val orientationEventListener by lazy {
        object : OrientationEventListener(requireContext()) {
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) {
                    return
                }

                val rotation = when (orientation) {
                    in 45 until 135 -> Surface.ROTATION_270
                    in 135 until 225 -> Surface.ROTATION_180
                    in 225 until 315 -> Surface.ROTATION_90
                    else -> Surface.ROTATION_0
                }

                imageCapture?.targetRotation = rotation
            }
        }
    }

//    private fun toggleFlash(isFlashOn: Boolean) {
//        if (isFlashOn) {
//            imageCapture?.flashMode = ImageCapture.FLASH_MODE_ON
//        } else {
//            imageCapture?.flashMode = ImageCapture.FLASH_MODE_OFF
//        }
//    }

    fun showLoading(isLoading: Boolean) {
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

    override fun onStart() {
        super.onStart()
        orientationEventListener.enable()
    }

    override fun onStop() {
        super.onStop()
        orientationEventListener.disable()
        imageCapture?.flashMode = ImageCapture.FLASH_MODE_OFF
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}