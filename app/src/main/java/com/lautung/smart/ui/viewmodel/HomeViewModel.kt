package com.lautung.smart.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lautung.smart.data.model.Device
import com.lautung.smart.data.repository.DeviceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class HomeViewModel(
    private val deviceRepository: DeviceRepository
) : ViewModel() {
    
    private val _devicesState = MutableStateFlow<UiState<List<Device>>>(UiState.Loading)
    val devicesState: StateFlow<UiState<List<Device>>> = _devicesState.asStateFlow()
    
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()
    
    init {
        loadDevices()
    }
    
    fun loadDevices() {
        viewModelScope.launch {
            _devicesState.value = UiState.Loading
            val result = deviceRepository.refreshDevices()
            result.onSuccess { devices ->
                _devicesState.value = UiState.Success(devices)
            }.onFailure { e ->
                _devicesState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
    
    fun refreshDevices() {
        viewModelScope.launch {
            _isRefreshing.value = true
            val result = deviceRepository.refreshDevices()
            _isRefreshing.value = false
            result.onFailure { e ->
                _devicesState.value = UiState.Error(e.message ?: "Failed to refresh")
            }
        }
    }
}
