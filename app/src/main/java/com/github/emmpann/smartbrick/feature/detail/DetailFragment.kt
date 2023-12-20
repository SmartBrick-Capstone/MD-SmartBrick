package com.github.emmpann.smartbrick.feature.detail

import android.app.Dialog
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.core.util.ImageUtil
import com.github.emmpann.smartbrick.databinding.FailedScanDialogBinding
import com.github.emmpann.smartbrick.databinding.FragmentDetailBinding
import com.github.emmpann.smartbrick.databinding.LayoutLoadingBinding
import com.github.emmpann.smartbrick.databinding.SuccessScanDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentDetailBinding
    private lateinit var dialog: Dialog

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

        viewModel.imageUploadResponse.observe(viewLifecycleOwner) {
            when (it) {
                is ResultApi.Success -> {
                    scanLoading(false)
                    if (it.data.prediction == "Benda ini layak dijadikan eco-brick") {
                        showSuccessDialog()
                        binding.tvResultTitle.visibility = View.VISIBLE
                        binding.tvStepEcobrick.visibility = View.VISIBLE
                        binding.tvResultTitle.text = it.data.prediction
                    } else {
                        // benda tidak layak
                        showFailedDialog()
                        binding.tvNotvalidEcobrickWaste.visibility = View.VISIBLE
                        binding.tvNotvalidEcobrickWaste.text = it.data.prediction
                    }
                }

                is ResultApi.Error -> {
                    scanLoading(false)
                    showErrorDialog(it.error)
                }

                is ResultApi.Loading -> {
                    scanLoading(true)
                }
            }
        }

        viewModel.setCurrentImage(DetailFragmentArgs.fromBundle(arguments as Bundle).imageUri.toUri())

        viewModel.currentImageUri.value?.let {
            lifecycleScope.launch {
                showImage(it)
                val imageFile = ImageUtil.uriToFile(it, requireContext())
                viewModel.uploadImage(imageFile)
            }
        }
    }

    private fun setClickListener() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_scanFragment)
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

    private fun scanLoading(isLoading: Boolean) {
        binding.loadingScan.visibility = if (isLoading) View.VISIBLE else View.GONE
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

    private fun showSuccessDialog() {
        if (::dialog.isInitialized.not()) {
            dialog = Dialog(requireContext(), R.style.Dialog_Loading)
            val dialogBinding = SuccessScanDialogBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.window?.apply {
                setLayout(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                )
            }
            dialogBinding.btnYes.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun showFailedDialog() {
        if (::dialog.isInitialized.not()) {
            dialog = Dialog(requireContext(), R.style.Dialog_Loading)
            val dialogBinding = FailedScanDialogBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.window?.apply {
                setLayout(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                )
            }
            dialogBinding.btnYes.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun showErrorDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.error))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->

            }.show()
    }
}