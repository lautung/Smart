package com.lautung.smart.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeviceDto(
    val id: String,
    val name: String,
    val type: String,
    val status: String,
    val isOnline: Boolean,
    val properties: Map<String, Any>?,
    val roomId: String?,
    val roomName: String?,
    val lastUpdated: Long
)

@JsonClass(generateAdapter = true)
data class SceneDto(
    val id: String,
    val name: String,
    val icon: String,
    val description: String?,
    val triggerType: String,
    val triggerConfig: Map<String, Any>?,
    val actions: List<SceneActionDto>,
    val isEnabled: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)

@JsonClass(generateAdapter = true)
data class SceneActionDto(
    val deviceId: String,
    val deviceName: String,
    val action: String,
    val parameters: Map<String, Any>?
)

@JsonClass(generateAdapter = true)
data class ProductDto(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    val isHot: Boolean,
    val stock: Int,
    val rating: Float,
    val reviewCount: Int
)
