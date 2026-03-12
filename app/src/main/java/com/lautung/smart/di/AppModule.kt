package com.lautung.smart.di

import com.lautung.smart.data.remote.api.SmartNestApi
import com.lautung.smart.data.repository.AuthRepository
import com.lautung.smart.data.repository.DeviceRepository
import com.lautung.smart.data.repository.ProductRepository
import com.lautung.smart.data.repository.SceneRepository
import com.lautung.smart.ui.viewmodel.AuthViewModel
import com.lautung.smart.ui.viewmodel.DeviceViewModel
import com.lautung.smart.ui.viewmodel.HomeViewModel
import com.lautung.smart.ui.viewmodel.MallViewModel
import com.lautung.smart.ui.viewmodel.ProfileViewModel
import com.lautung.smart.ui.viewmodel.SceneViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.smartnest.example.com/"

val networkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    
    single { get<Retrofit>().create(SmartNestApi::class.java) }
}

val repositoryModule = module {
    single { DeviceRepository(get()) }
    single { AuthRepository(get(), androidContext()) }
    single { ProductRepository(get()) }
    single { SceneRepository(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { DeviceViewModel(get()) }
    viewModel { SceneViewModel(get()) }
    viewModel { MallViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}

val appModules = listOf(
    networkModule,
    repositoryModule,
    viewModelModule
)
