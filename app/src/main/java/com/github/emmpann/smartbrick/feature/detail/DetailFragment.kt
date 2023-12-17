package com.github.emmpann.smartbrick.feature.detail

import android.app.Dialog
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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.core.util.ImageUtil
import com.github.emmpann.smartbrick.databinding.FailedScanDialogBinding
import com.github.emmpann.smartbrick.databinding.FragmentDetailBinding
import com.github.emmpann.smartbrick.databinding.SuccessScanDialogBinding
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
        with(viewModel) {
            setCurrentImage(DetailFragmentArgs.fromBundle(arguments as Bundle).imageUri.toUri())

            currentImageUri.value?.let {
                lifecycleScope.launch {
                    showImage(it)

                    val imageFile = ImageUtil.uriToFile(it, requireContext())
                    uploadImage(imageFile)
                }
            }

            imageUploadResponse.observe(viewLifecycleOwner) {

            }
        }
        showFailedDialog(true)
    }

    private fun setClickListener() {
        with(binding) {
            icBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnBack.setOnClickListener {
                findNavController().navigate(R.id.action_detailFragment_to_scanFragment)
            }
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

    fun showLoading(isLoading: Boolean) {
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
        }

        if (isLoading) dialog.show() else dialog.hide()
    }

    private fun showSuccessDialog(isShow: Boolean) {
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

        if (isShow) dialog.show() else dialog.hide()
    }

    private fun showFailedDialog(isShow: Boolean) {
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

        if (isShow) dialog.show() else dialog.hide()
    }
}