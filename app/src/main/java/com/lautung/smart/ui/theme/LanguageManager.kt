package com.lautung.smart.ui.theme

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class Language(val code: String, val displayName: String) {
    CHINESE("zh", "简体中文"),
    ENGLISH("en", "English")
}

class LanguageManager(private val context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("language_prefs", Context.MODE_PRIVATE)
    
    private val _language = MutableStateFlow(getStoredLanguage())
    val language: StateFlow<Language> = _language.asStateFlow()

    private fun getStoredLanguage(): Language {
        val code = prefs.getString("language_code", Language.CHINESE.code) ?: Language.CHINESE.code
        return Language.entries.find { it.code == code } ?: Language.CHINESE
    }

    fun setLanguage(language: Language) {
        _language.value = language
        prefs.edit().putString("language_code", language.code).apply()
    }

    fun getCurrentLanguage(): Language = _language.value
}

@Composable
fun rememberLanguageManager(context: Context): LanguageManager {
    return LanguageManager(context)
}
