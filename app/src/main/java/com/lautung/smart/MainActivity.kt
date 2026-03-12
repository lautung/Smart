package com.lautung.smart

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.lautung.smart.di.appModules
import com.lautung.smart.ui.theme.LanguageManager
import com.lautung.smart.ui.theme.SmartTheme
import com.lautung.smart.ui.theme.ThemeManager
import com.lautung.smart.ui.theme.ThemeMode
import com.lautung.smart.ui.screens.SmartNestApp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    private val themeManager by lazy { ThemeManager(this) }
    private lateinit var languageManager: LanguageManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModules)
        }

        languageManager = LanguageManager(this)

        // 启用边缘到边缘
        enableEdgeToEdge()

        // 设置窗口：允许内容延伸到系统栏后面
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Load saved theme preference
        lifecycleScope.launch {
            themeManager.loadSavedTheme()
        }

        setContent {
            val currentTheme = themeManager.themeMode.collectAsState()

            // 根据主题动态设置状态栏图标颜色
            val isDarkTheme = when (currentTheme.value) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            // 动态设置状态栏颜色
            StatusBarColorEffect(isDarkTheme)

            SmartTheme(themeManager = themeManager) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SmartNestApp(
                        themeManager = themeManager,
                        languageManager = languageManager
                    )
                }
            }
        }
    }
}

/**
 * 根据主题动态设置状态栏图标颜色
 */
@Composable
private fun StatusBarColorEffect(isDarkTheme: Boolean) {
    val view = LocalView.current
    LaunchedEffect(isDarkTheme) {
        val window = (view.context as? android.app.Activity)?.window
        window?.let {
            val controller = WindowCompat.getInsetsController(it, view)
            controller.isAppearanceLightStatusBars = !isDarkTheme
        }
    }
}
