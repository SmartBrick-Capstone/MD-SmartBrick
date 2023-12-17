package com.github.emmpann.smartbrick.feature.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.emmpann.smartbrick.core.data.repository.ImageRepository
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

    private val _isFlashOn = MutableLiveData(false)
    val isFlashOn: LiveData<Boolean> get() = _isFlashOn

    fun uploadImage(imageFile: File) = viewModelScope.launch{
        imageRepository.uploadImage(imageFile)
    }

    fun setFlashLight() = viewModelScope.launch{
        _isFlashOn.value = _isFlashOn.value?.not()
    }
}