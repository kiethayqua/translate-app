package vn.kietnguyendev.translateapp.presentation.setting

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import vn.kietnguyendev.translateapp.util.LanguageUtil

const val DEFAULT_LANG_KEY = "DEFAULT_LANG_KEY"
const val SECOND_LANG_KEY = "SECOND_LANG_KEY"

class SettingViewModel: ViewModel() {
    private val _state = mutableStateOf(SettingState())
    val state: State<SettingState> = _state
    fun setLanguage(context: Context, key: String, lang: String) {
        LanguageUtil.setLanguage(context, key, lang)
        getLangs(context)
    }
    fun getLangs(context: Context) {
        val defaultLang = LanguageUtil.getLang(context, DEFAULT_LANG_KEY) ?: SettingState.default_lang
        val secondLang = LanguageUtil.getLang(context, SECOND_LANG_KEY) ?: SettingState.second_lang

        _state.value = state.value.copy(
            defaultLang = defaultLang,
            secondLang = secondLang
        )
    }
}