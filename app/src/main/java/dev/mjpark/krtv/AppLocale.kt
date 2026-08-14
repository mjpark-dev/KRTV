package dev.mjpark.krtv

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object AppLocale {
    private const val PREFS = "krtv_locale"
    private const val KEY_LANGUAGE = "language"
    const val DEFAULT_LANGUAGE = "ko"

    val supportedLanguages = listOf("ko", "zh-CN", "zh-TW", "en", "system")
    val languageNames = listOf("한국어", "简体中文", "繁體中文", "English", "跟随系统")

    fun current(context: Context): String =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(KEY_LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE

    fun set(context: Context, language: String) {
        require(language in supportedLanguages)
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(KEY_LANGUAGE, language).apply()
    }

    fun displayName(context: Context): String {
        val index = supportedLanguages.indexOf(current(context)).coerceAtLeast(0)
        return languageNames[index]
    }

    fun wrap(base: Context): Context {
        val language = current(base)
        if (language == "system") {
            return base
        }

        val locale = Locale.forLanguageTag(language)
        Locale.setDefault(locale)
        val configuration = Configuration(base.resources.configuration)
        configuration.setLocale(locale)
        return base.createConfigurationContext(configuration)
    }
}
