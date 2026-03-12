package com.lautung.smart.data.model

data class Device(
    val id: String,
    val name: String,
    val type: DeviceType,
    val status: String,
    val isOnline: Boolean = true
)

enum class DeviceType {
    AIR_CONDITIONER,
    LIGHT,
    DOOR_LOCK,
    CAMERA,
    THERMOSTAT
}

data class Scene(
    val id: String,
    val name: String,
    val icon: String,
    val description: String,
    val isEnabled: Boolean,
    val schedule: String? = null
)

data class Product(
    val id: String,
    val name: String,
    val price: Int,
    val description: String,
    val icon: String
)

data class User(
    val id: String,
    val name: String,
    val email: String,
    val avatar: String,
    val role: UserRole
)

enum class UserRole {
    ADMIN,
    MEMBER
}

data class EnergyUsage(
    val deviceName: String,
    val usage: Double,
    val icon: String
)

data class CommunityPost(
    val id: String,
    val author: String,
    val authorAvatar: String,
    val content: String,
    val timeAgo: String
)
