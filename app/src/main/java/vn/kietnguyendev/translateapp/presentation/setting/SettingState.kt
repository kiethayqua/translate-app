package vn.kietnguyendev.translateapp.presentation.setting

import com.google.mlkit.nl.translate.TranslateLanguage
import javax.annotation.concurrent.Immutable

@Immutable
data class SettingState(
    val defaultLang: String = default_lang,
    val secondLang: String = second_lang
) {
    companion object {
        val default_lang = TranslateLanguage.ENGLISH
        val second_lang = TranslateLanguage.VIETNAMESE
    }
}
