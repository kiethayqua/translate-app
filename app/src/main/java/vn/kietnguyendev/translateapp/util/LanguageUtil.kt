package vn.kietnguyendev.translateapp.util

import android.content.Context
import com.google.mlkit.nl.translate.TranslateLanguage

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
}