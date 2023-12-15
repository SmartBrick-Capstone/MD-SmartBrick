package com.github.emmpann.smartbrick.feature.scan

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ScanViewModel @Inject constructor() : ViewModel() {
    private val _imageResponse = MutableLiveData<Uri>()

    val imageResponse: LiveData<Uri> get() = _imageResponse

    fun setImageUri(image: Uri) {
        _imageResponse.value = image
    }
}