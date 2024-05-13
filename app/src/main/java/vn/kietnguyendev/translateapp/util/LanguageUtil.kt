package vn.kietnguyendev.translateapp.util

import android.content.Context
import com.google.mlkit.nl.translate.TranslateLanguage
import java.util.Locale

object LanguageUtil {
    fun getLanguageFromCode(code: String): String {
        return when (code) {
            TranslateLanguage.ENGLISH -> "English"
            TranslateLanguage.VIETNAMESE -> "Vietnamese"
            TranslateLanguage.CHINESE -> "Chinese"
            TranslateLanguage.JAPANESE -> "Japanese"
            TranslateLanguage.FRENCH -> "French"
            else -> ""
        }
    }

    fun setLanguage(context: Context, key: String, lang: String) {
        context.getSharedPreferences(key, Context.MODE_PRIVATE)
            .edit()
            .putString(key, lang)
            .apply()
    }
    fun getLang(context: Context, key: String): String? {
        return context.getSharedPreferences(
            key,
            Context.MODE_PRIVATE
        ).getString(key, null)
    }

    fun getLocale(lang: String): Locale {
        return when (lang) {
            TranslateLanguage.ENGLISH -> Locale.UK
            TranslateLanguage.VIETNAMESE -> Locale.UK
            TranslateLanguage.CHINESE -> Locale.CHINESE
            TranslateLanguage.JAPANESE -> Locale.JAPANESE
            TranslateLanguage.FRENCH -> Locale.FRENCH
            else -> Locale.getDefault()
        }
    }
}