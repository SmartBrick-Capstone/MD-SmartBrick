package com.github.emmpann.smartbrick.feature.detail

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : ViewModel() {
    private val _currentImageUri = MutableLiveData<Uri>()

    val currentImageUri: LiveData<Uri> get() = _currentImageUri

    fun setCurrentImage(uri: Uri) {
        _currentImageUri.value = uri
    }
}