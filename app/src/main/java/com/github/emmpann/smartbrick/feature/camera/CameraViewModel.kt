package com.github.emmpann.smartbrick.feature.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.emmpann.smartbrick.core.data.remote.response.PredictResponse
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.core.data.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _isFlashOn = MutableLiveData(false)
    val isFlashOn: LiveData<Boolean> get() = _isFlashOn

    fun setFlashLight() = viewModelScope.launch{
        _isFlashOn.value = _isFlashOn.value?.not()
    }
}