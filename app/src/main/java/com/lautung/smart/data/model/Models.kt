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
    val schedule: String? = null,
    val triggerType: String = "manual"
)

data class SceneAction(
    val deviceId: String,
    val deviceName: String,
    val action: String,
    val parameters: Map<String, Any>? = null
)

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val description: String,
    val imageUrl: String = "",
    val category: String = "",
    val isHot: Boolean = false,
    val stock: Int = 0,
    val rating: Float = 0f,
    val reviewCount: Int = 0
) {
    val displayIcon: String
        get() = when (category) {
            "lighting" -> "💡"
            "climate" -> "🌡️"
            "security" -> if (name.contains("门锁")) "🔒" else "📷"
            "curtain" -> "🌟"
            "audio" -> "🔊"
            else -> "🔌"
        }
    val displayPrice: String get() = "¥${price.toInt()}"
}

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
