package com.lautung.smart.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lautung.smart.BuildConfig
import com.lautung.smart.data.model.Device
import com.lautung.smart.data.model.DeviceType
import com.lautung.smart.data.model.Product
import com.lautung.smart.data.model.Scene
import com.lautung.smart.data.model.SceneAction
import com.lautung.smart.data.model.User
import com.lautung.smart.data.model.UserRole
import com.lautung.smart.data.remote.api.SmartNestApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

private fun String.toDeviceType(): DeviceType {
    return try {
        DeviceType.valueOf(this.uppercase())
    } catch (e: IllegalArgumentException) {
        DeviceType.LIGHT // default fallback
    }
}

class DeviceRepository(
    private val api: SmartNestApi,
    private val deviceDao: com.lautung.smart.data.local.DeviceDao
) {
    private val _devices = MutableStateFlow<List<Device>>(emptyList())
    val devices: StateFlow<List<Device>> = _devices.asStateFlow()

    suspend fun refreshDevices(): Result<List<Device>> {
        // 如果使用模拟数据，直接返回模拟数据（模拟网络延迟）
        if (BuildConfig.USE_MOCK_DATA) {
            delay(500) // 模拟网络延迟
            val mockDevices = getMockDevices()
            _devices.value = mockDevices
            return Result.success(mockDevices)
        }

        // 使用真实 API
        return try {
            val response = api.getDevices()
            if (response.isSuccessful) {
                val devices = response.body()?.map { dto ->
                    Device(
                        id = dto.id,
                        name = dto.name,
                        type = dto.type.toDeviceType(),
                        status = dto.status,
                        isOnline = dto.isOnline
                    )
                } ?: emptyList()
                _devices.value = devices
                Result.success(devices)
            } else {
                Result.failure(Exception("Failed to fetch devices: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getMockDevices(): List<Device> {
        return listOf(
            Device("1", "客厅空调", DeviceType.AIR_CONDITIONER, "已开启", true),
            Device("2", "客厅灯", DeviceType.LIGHT, "已开启", true),
            Device("3", "智能门锁", DeviceType.DOOR_LOCK, "已锁定", true),
            Device("4", "摄像头", DeviceType.CAMERA, "在线", true),
            Device("5", "温控器", DeviceType.THERMOSTAT, "24°C", true)
        )
    }
    
    suspend fun controlDevice(deviceId: String, action: String): Result<Device> {
        return try {
            val response = api.controlDevice(deviceId, action)
            if (response.isSuccessful) {
                val dto = response.body()
                if (dto != null) {
                    val device = Device(
                        id = dto.id,
                        name = dto.name,
                        type = dto.type.toDeviceType(),
                        status = dto.status,
                        isOnline = dto.isOnline
                    )
                    _devices.value = _devices.value.map { if (it.id == deviceId) device else it }
                    Result.success(device)
                } else {
                    Result.failure(Exception("Device not found"))
                }
            } else {
                Result.failure(Exception("Failed to control device: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class AuthRepository(
    private val api: SmartNestApi,
    private val dataStore: androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences>
) {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    init {
        if (BuildConfig.USE_MOCK_DATA) {
            setMockUser()
        }
    }

    val isLoggedIn: Flow<Boolean> = dataStore.data.map { prefs ->
        if (BuildConfig.USE_MOCK_DATA) {
            _currentUser.value != null
        } else {
            prefs[ACCESS_TOKEN_KEY] != null
        }
    }

    suspend fun login(email: String, password: String): Result<Unit> {
        if (BuildConfig.USE_MOCK_DATA) {
            delay(500)
            _currentUser.value = User(
                id = "1",
                name = "Alex Chen",
                email = email,
                avatar = "",
                role = UserRole.ADMIN
            )
            return Result.success(Unit)
        }
        return try {
            val response = api.login(
                com.lautung.smart.data.remote.dto.LoginRequest(email, password)
            )
            if (response.isSuccessful) {
                response.body()?.let { authResponse ->
                    saveTokens(authResponse.accessToken, authResponse.refreshToken)
                    _currentUser.value = User(
                        id = authResponse.user.id,
                        email = authResponse.user.email,
                        name = authResponse.user.name,
                        avatar = authResponse.user.avatar ?: "",
                        role = UserRole.MEMBER
                    )
                }
                Result.success(Unit)
            } else {
                Result.failure(Exception("Login failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun setMockUser() {
        if (BuildConfig.USE_MOCK_DATA && _currentUser.value == null) {
            _currentUser.value = User(
                id = "1",
                name = "Alex Chen",
                email = "alex@smartnest.com",
                avatar = "",
                role = UserRole.ADMIN
            )
        }
    }

    suspend fun logout() {
        try {
            api.logout()
        } catch (_: Exception) {
        }
        dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN_KEY)
            prefs.remove(REFRESH_TOKEN_KEY)
        }
        _currentUser.value = null
    }
    
    suspend fun getAccessToken(): String? {
        return dataStore.data.first()[ACCESS_TOKEN_KEY]
    }
    
    private suspend fun saveTokens(accessToken: String, refreshToken: String) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = accessToken
            prefs[REFRESH_TOKEN_KEY] = refreshToken
        }
    }
}

class ProductRepository(
    private val api: SmartNestApi,
    private val productDao: com.lautung.smart.data.local.ProductDao
) {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()
    
    private val _hotProducts = MutableStateFlow<List<Product>>(emptyList())
    val hotProducts: StateFlow<List<Product>> = _hotProducts.asStateFlow()
    
    suspend fun refreshProducts(): Result<List<Product>> {
        // 如果使用模拟数据
        if (BuildConfig.USE_MOCK_DATA) {
            delay(500) // 模拟网络延迟
            val mockProducts = getMockProducts()
            _products.value = mockProducts
            return Result.success(mockProducts)
        }

        // 使用真实 API
        return try {
            val response = api.getProducts()
            if (response.isSuccessful) {
                val products = response.body()?.map { dto ->
                    Product(
                        id = dto.id,
                        name = dto.name,
                        description = dto.description,
                        price = dto.price,
                        imageUrl = dto.imageUrl,
                        category = dto.category,
                        isHot = dto.isHot,
                        stock = dto.stock,
                        rating = dto.rating,
                        reviewCount = dto.reviewCount
                    )
                } ?: emptyList()
                _products.value = products
                Result.success(products)
            } else {
                Result.failure(Exception("Failed to fetch products: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getMockProducts(): List<Product> {
        return listOf(
            Product("1", "智能氛围灯", 299.0, "1600 万色，语音控制", "", "lighting", true, 100, 4.5f, 128),
            Product("2", "智能温控器", 599.0, "精准控温，节能舒适", "", "climate", true, 50, 4.8f, 256),
            Product("3", "智能门锁", 1299.0, "指纹识别，远程解锁", "", "security", true, 30, 4.7f, 89),
            Product("4", "智能摄像头", 399.0, "高清画质，移动侦测", "", "security", false, 80, 4.3f, 67),
            Product("5", "智能窗帘电机", 699.0, "自动开合，语音控制", "", "curtain", false, 40, 4.6f, 45),
            Product("6", "智能音箱", 299.0, "语音助手，音乐播放", "", "audio", false, 120, 4.4f, 198)
        )
    }

    suspend fun refreshHotProducts(): Result<List<Product>> {
        // 如果使用模拟数据
        if (BuildConfig.USE_MOCK_DATA) {
            delay(500)
            val mockHotProducts = getMockHotProducts()
            _hotProducts.value = mockHotProducts
            return Result.success(mockHotProducts)
        }

        // 使用真实 API
        return try {
            val response = api.getHotProducts()
            if (response.isSuccessful) {
                val products = response.body()?.map { dto ->
                    Product(
                        id = dto.id,
                        name = dto.name,
                        description = dto.description,
                        price = dto.price,
                        imageUrl = dto.imageUrl,
                        category = dto.category,
                        isHot = dto.isHot,
                        stock = dto.stock,
                        rating = dto.rating,
                        reviewCount = dto.reviewCount
                    )
                } ?: emptyList()
                _hotProducts.value = products
                Result.success(products)
            } else {
                Result.failure(Exception("Failed to fetch hot products: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getMockHotProducts(): List<Product> {
        return listOf(
            Product("1", "智能氛围灯", 299.0, "1600 万色，语音控制", "", "lighting", true, 100, 4.5f, 128),
            Product("2", "智能温控器", 599.0, "精准控温，节能舒适", "", "climate", true, 50, 4.8f, 256),
            Product("3", "智能门锁", 1299.0, "指纹识别，远程解锁", "", "security", true, 30, 4.7f, 89)
        )
    }
}

class SceneRepository(
    private val api: SmartNestApi,
    private val sceneDao: com.lautung.smart.data.local.SceneDao
) {
    private val _scenes = MutableStateFlow<List<Scene>>(emptyList())
    val scenes: StateFlow<List<Scene>> = _scenes.asStateFlow()

    suspend fun refreshScenes(): Result<List<Scene>> {
        if (BuildConfig.USE_MOCK_DATA) {
            delay(500)
            val mockScenes = getMockScenes()
            _scenes.value = mockScenes
            return Result.success(mockScenes)
        }
        return try {
            val response = api.getScenes()
            if (response.isSuccessful) {
                val scenes = response.body()?.map { dto ->
                    Scene(
                        id = dto.id,
                        name = dto.name,
                        icon = dto.icon,
                        description = dto.description ?: "",
                        isEnabled = dto.isEnabled,
                        triggerType = dto.triggerType
                    )
                } ?: emptyList()
                _scenes.value = scenes
                Result.success(scenes)
            } else {
                Result.failure(Exception("Failed to fetch scenes: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getMockScenes(): List<Scene> {
        return listOf(
            Scene("1", "起床模式", "☀️", "工作日 7:00 开启窗帘，播放音乐，咖啡机启动。", true, "工作日 7:00", "time"),
            Scene("2", "睡眠模式", "🌙", "每天 23:00 关闭所有灯，空调调至睡眠温度。", false, "每天 23:00", "time"),
            Scene("3", "回家模式", "🏠", "地理位置触发，开启门厅灯，空调启动，播放欢迎语音。", true, null, "geofence")
        )
    }

    suspend fun createScene(scene: Scene): Result<Scene> {
        if (BuildConfig.USE_MOCK_DATA) {
            delay(300)
            _scenes.value = _scenes.value + scene
            return Result.success(scene)
        }
        return try {
            val dto = com.lautung.smart.data.remote.dto.SceneDto(
                id = scene.id,
                name = scene.name,
                icon = scene.icon,
                description = scene.description,
                triggerType = scene.triggerType,
                triggerConfig = null,
                actions = emptyList(),
                isEnabled = scene.isEnabled,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            val response = api.createScene(dto)
            if (response.isSuccessful) {
                _scenes.value = _scenes.value + scene
                Result.success(scene)
            } else {
                Result.failure(Exception("Failed to create scene: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteScene(sceneId: String): Result<Unit> {
        if (BuildConfig.USE_MOCK_DATA) {
            delay(300)
            _scenes.value = _scenes.value.filter { it.id != sceneId }
            return Result.success(Unit)
        }
        return try {
            val response = api.deleteScene(sceneId)
            if (response.isSuccessful) {
                _scenes.value = _scenes.value.filter { it.id != sceneId }
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to delete scene: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun executeScene(sceneId: String): Result<Unit> {
        if (BuildConfig.USE_MOCK_DATA) {
            delay(300)
            return Result.success(Unit)
        }
        return try {
            val response = api.executeScene(sceneId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to execute scene: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun toggleScene(sceneId: String, enabled: Boolean) {
        _scenes.value = _scenes.value.map {
            if (it.id == sceneId) it.copy(isEnabled = enabled) else it
        }
    }
}
