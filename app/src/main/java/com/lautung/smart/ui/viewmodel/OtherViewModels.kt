package com.lautung.smart.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lautung.smart.data.model.Device
import com.lautung.smart.data.repository.AuthRepository
import com.lautung.smart.data.repository.DeviceRepository
import com.lautung.smart.data.repository.Product
import com.lautung.smart.data.repository.ProductRepository
import com.lautung.smart.data.repository.Scene
import com.lautung.smart.data.repository.SceneRepository
import com.lautung.smart.data.repository.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthState(
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    
    init {
        checkLoginStatus()
    }
    
    private fun checkLoginStatus() {
        viewModelScope.launch {
            authRepository.isLoggedIn.collect { isLoggedIn ->
                _authState.value = _authState.value.copy(isLoggedIn = isLoggedIn)
            }
        }
    }
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            
            authRepository.login(email, password)
                .onSuccess {
                    _authState.value = _authState.value.copy(
                        isLoggedIn = true,
                        isLoading = false
                    )
                }
                .onFailure { e ->
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Login failed"
                    )
                }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true)
            authRepository.logout()
            _authState.value = AuthState()
        }
    }
    
    fun clearError() {
        _authState.value = _authState.value.copy(error = null)
    }
}

class DeviceViewModel(
    private val deviceRepository: DeviceRepository
) : ViewModel() {
    
    private val _devicesState = MutableStateFlow<UiState<List<Device>>>(UiState.Loading)
    val devicesState: StateFlow<UiState<List<Device>>> = _devicesState.asStateFlow()
    
    private val _selectedDevice = MutableStateFlow<Device?>(null)
    val selectedDevice: StateFlow<Device?> = _selectedDevice.asStateFlow()
    
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
    
    fun controlDevice(deviceId: String, action: String) {
        viewModelScope.launch {
            deviceRepository.controlDevice(deviceId, action)
                .onSuccess { device ->
                    _selectedDevice.value = device
                }
                .onFailure { e ->
                    _devicesState.value = UiState.Error(e.message ?: "Control failed")
                }
        }
    }
    
    fun selectDevice(device: Device) {
        _selectedDevice.value = device
    }
}

class SceneViewModel(
    private val sceneRepository: SceneRepository
) : ViewModel() {

    private val _scenesState = MutableStateFlow<UiState<List<Scene>>>(UiState.Loading)
    val scenesState: StateFlow<UiState<List<Scene>>> = _scenesState.asStateFlow()

    init {
        loadScenes()
    }

    fun loadScenes() {
        viewModelScope.launch {
            _scenesState.value = UiState.Loading
            val result = sceneRepository.refreshScenes()
            result.onSuccess { scenes ->
                _scenesState.value = UiState.Success(scenes)
            }.onFailure { e ->
                _scenesState.value = UiState.Error(e.message ?: "Failed to load scenes")
            }
        }
    }

    fun createScene(scene: Scene) {
        viewModelScope.launch {
            val result = sceneRepository.createScene(scene)
            result.onSuccess { createdScene ->
                _scenesState.value = UiState.Success(
                    (_scenesState.value as? UiState.Success)?.data?.plus(createdScene) ?: listOf(createdScene)
                )
            }.onFailure { e ->
                _scenesState.value = UiState.Error(e.message ?: "Failed to create scene")
            }
        }
    }

    fun deleteScene(sceneId: String) {
        viewModelScope.launch {
            val result = sceneRepository.deleteScene(sceneId)
            result.onSuccess {
                _scenesState.value = UiState.Success(
                    (_scenesState.value as? UiState.Success)?.data?.filter { it.id != sceneId } ?: emptyList()
                )
            }.onFailure { e ->
                _scenesState.value = UiState.Error(e.message ?: "Failed to delete scene")
            }
        }
    }

    fun executeScene(sceneId: String) {
        viewModelScope.launch {
            sceneRepository.executeScene(sceneId)
        }
    }

    fun refreshScenes() {
        loadScenes()
    }
}

class MallViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {
    
    private val _productsState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val productsState: StateFlow<UiState<List<Product>>> = _productsState.asStateFlow()
    
    private val _hotProductsState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val hotProductsState: StateFlow<UiState<List<Product>>> = _hotProductsState.asStateFlow()
    
    init {
        loadProducts()
        loadHotProducts()
    }
    
    fun loadProducts() {
        viewModelScope.launch {
            _productsState.value = UiState.Loading
            val result = productRepository.refreshProducts()
            result.onSuccess { products ->
                _productsState.value = UiState.Success(products)
            }.onFailure { e ->
                _productsState.value = UiState.Error(e.message ?: "Failed to load products")
            }
        }
    }
    
    fun loadHotProducts() {
        viewModelScope.launch {
            _hotProductsState.value = UiState.Loading
            val result = productRepository.refreshHotProducts()
            result.onSuccess { products ->
                _hotProductsState.value = UiState.Success(products)
            }.onFailure { e ->
                _hotProductsState.value = UiState.Error(e.message ?: "Failed to load hot products")
            }
        }
    }
    
    fun refreshProducts() {
        loadProducts()
    }
}

class ProfileViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow<UiState<User?>>(UiState.Loading)
    val profileState: StateFlow<UiState<User?>> = _profileState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _profileState.value = UiState.Loading
            authRepository.currentUser.collect { user ->
                _profileState.value = UiState.Success(user)
            }
        }
    }

    fun updateProfile(name: String, avatar: String?) {
        viewModelScope.launch {
            val currentUser = (_profileState.value as? UiState.Success)?.data
            if (currentUser != null) {
                val updatedUser = currentUser.copy(name = name, avatar = avatar)
                _profileState.value = UiState.Success(updatedUser)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _profileState.value = UiState.Success(null)
        }
    }
}
