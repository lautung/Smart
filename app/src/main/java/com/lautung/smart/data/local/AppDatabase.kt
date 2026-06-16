package com.lautung.smart.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 设备实体
 */
@Entity(tableName = "devices")
data class DeviceEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val status: String,
    val isOnline: Boolean,
    val roomName: String? = null
)

/**
 * 产品实体
 */
@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
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

/**
 * 场景实体
 */
@Entity(tableName = "scenes")
data class SceneEntity(
    @PrimaryKey val id: String,
    val name: String,
    val icon: String,
    val description: String?,
    val isEnabled: Boolean
)

/**
 * Room 数据库
 */
@Database(
    entities = [DeviceEntity::class, ProductEntity::class, SceneEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao
    abstract fun productDao(): ProductDao
    abstract fun sceneDao(): SceneDao

    companion object {
        private const val DATABASE_NAME = "smart_nest_db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
