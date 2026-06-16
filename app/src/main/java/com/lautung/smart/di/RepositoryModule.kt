package com.lautung.smart.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.lautung.smart.data.local.AppDatabase
import com.lautung.smart.data.local.DeviceDao
import com.lautung.smart.data.local.ProductDao
import com.lautung.smart.data.local.SceneDao
import com.lautung.smart.data.remote.api.SmartNestApi
import com.lautung.smart.data.repository.AuthRepository
import com.lautung.smart.data.repository.DeviceRepository
import com.lautung.smart.data.repository.ProductRepository
import com.lautung.smart.data.repository.SceneRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideDeviceRepository(
        api: SmartNestApi,
        deviceDao: DeviceDao
    ): DeviceRepository {
        return DeviceRepository(api, deviceDao)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: SmartNestApi,
        dataStore: DataStore<Preferences>
    ): AuthRepository {
        return AuthRepository(api, dataStore)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        api: SmartNestApi,
        productDao: ProductDao
    ): ProductRepository {
        return ProductRepository(api, productDao)
    }

    @Provides
    @Singleton
    fun provideSceneRepository(
        api: SmartNestApi,
        sceneDao: SceneDao
    ): SceneRepository {
        return SceneRepository(api, sceneDao)
    }
}
