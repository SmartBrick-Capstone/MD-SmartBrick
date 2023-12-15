package com.github.emmpann.smartbrick.feature.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.emmpann.smartbrick.core.data.local.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _scanResponse = MutableLiveData<String>()

    val scanResponse: LiveData<String> get() = _scanResponse

    fun uploadImage(imageFile: File) = viewModelScope.launch{
        imageRepository.uploadImage(imageFile)
    }

}