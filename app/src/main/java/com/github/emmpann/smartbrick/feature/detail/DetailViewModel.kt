package com.github.emmpann.smartbrick.feature.detail

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.emmpann.smartbrick.core.data.repository.ImageRepository
import com.github.emmpann.smartbrick.core.data.remote.response.PredictResponse
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {
    private val _currentImageUri = MutableLiveData<Uri>()

    val currentImageUri: LiveData<Uri> get() = _currentImageUri

    private val _imageUploadResponse = MutableLiveData<ResultApi<PredictResponse>>()

    val imageUploadResponse: LiveData<ResultApi<PredictResponse>> = _imageUploadResponse

    fun setCurrentImage(uri: Uri) {
        _currentImageUri.value = uri
    }

    fun uploadImage(imageFile: File) = viewModelScope.launch {
        imageRepository.predictImage(imageFile).collect {
            _imageUploadResponse.value = it
        }
    }
}