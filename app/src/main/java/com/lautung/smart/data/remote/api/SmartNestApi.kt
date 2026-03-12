package com.lautung.smart.data.remote.api

import com.lautung.smart.data.remote.dto.AuthResponse
import com.lautung.smart.data.remote.dto.DeviceDto
import com.lautung.smart.data.remote.dto.LoginRequest
import com.lautung.smart.data.remote.dto.ProductDto
import com.lautung.smart.data.remote.dto.RefreshRequest
import com.lautung.smart.data.remote.dto.RegisterRequest
import com.lautung.smart.data.remote.dto.SceneDto
import com.lautung.smart.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SmartNestApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
    
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
    
    @POST("auth/refresh")
    suspend fun refreshToken(@Body request: RefreshRequest): Response<AuthResponse>
    
    @GET("auth/me")
    suspend fun getCurrentUser(): Response<UserDto>
    
    @POST("auth/logout")
    suspend fun logout(): Response<Unit>
    
    @GET("devices")
    suspend fun getDevices(): Response<List<DeviceDto>>
    
    @GET("devices/{id}")
    suspend fun getDevice(@Path("id") id: String): Response<DeviceDto>
    
    @PUT("devices/{id}")
    suspend fun updateDevice(@Path("id") id: String, @Body device: DeviceDto): Response<DeviceDto>
    
    @POST("devices/{id}/control")
    suspend fun controlDevice(@Path("id") id: String, @Query("action") action: String): Response<DeviceDto>
    
    @GET("scenes")
    suspend fun getScenes(): Response<List<SceneDto>>
    
    @GET("scenes/{id}")
    suspend fun getScene(@Path("id") id: String): Response<SceneDto>
    
    @POST("scenes")
    suspend fun createScene(@Body scene: SceneDto): Response<SceneDto>
    
    @PUT("scenes/{id}")
    suspend fun updateScene(@Path("id") id: String, @Body scene: SceneDto): Response<SceneDto>
    
    @DELETE("scenes/{id}")
    suspend fun deleteScene(@Path("id") id: String): Response<Unit>
    
    @POST("scenes/{id}/execute")
    suspend fun executeScene(@Path("id") id: String): Response<Unit>
    
    @GET("products")
    suspend fun getProducts(): Response<List<ProductDto>>
    
    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: String): Response<ProductDto>
    
    @GET("products/hot")
    suspend fun getHotProducts(): Response<List<ProductDto>>
}
