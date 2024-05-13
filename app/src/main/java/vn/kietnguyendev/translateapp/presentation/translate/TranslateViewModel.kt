package vn.kietnguyendev.translateapp.presentation.translate

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import vn.kietnguyendev.translateapp.data.local.bookmark.Bookmark
import vn.kietnguyendev.translateapp.data.local.bookmark.BookmarkRepository
import vn.kietnguyendev.translateapp.presentation.setting.DEFAULT_LANG_KEY
import vn.kietnguyendev.translateapp.presentation.setting.SECOND_LANG_KEY
import vn.kietnguyendev.translateapp.presentation.setting.SettingState
import vn.kietnguyendev.translateapp.util.LanguageUtil
import javax.inject.Inject

@HiltViewModel
class TranslateViewModel @Inject constructor(private val bookmarkRepository: BookmarkRepository): ViewModel() {
    private val _state = mutableStateOf(TranslateState())
    val state: State<TranslateState> = _state

    fun initWithText(context: Context, initialText: String) {
        val defaultLang = LanguageUtil.getLang(context, DEFAULT_LANG_KEY) ?: SettingState.default_lang
        val secondLang = LanguageUtil.getLang(context, SECOND_LANG_KEY) ?: SettingState.second_lang
        _state.value = _state.value.copy(from = defaultLang, to = secondLang, fromText = initialText)
        onTranslate(initialText)
    }

    fun onChangeText(text: String) {
        _state.value = _state.value.copy(fromText = text)
    }

    fun onTranslate(text: String) {
        if (text.isEmpty()) return
        viewModelScope.launch(Dispatchers.IO) {
            translateFlow(text).collect {
                _state.value = _state.value.copy(toText = it)
            }
        }
    }

    fun onBookmark(fromText: String, toText: String) {
        viewModelScope.launch {
            bookmarkRepository.insertBookmarks(listOf(Bookmark(fromText = fromText, toText = toText)))
                .flowOn(Dispatchers.IO)
                .collect {}
        }
    }

    fun onSwapLang() {
        val from = _state.value.to
        val to = _state.value.from
        _state.value = _state.value.copy(from = from, to = to)
    }

    private fun translateFlow(text: String): Flow<String> {
        return callbackFlow {
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(_state.value.from)
                .setTargetLanguage(_state.value.to)
                .build()
            val englishVietnameseTranslator = Translation.getClient(options)
            val conditions = DownloadConditions.Builder().requireWifi().build()
            englishVietnameseTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener {
                    englishVietnameseTranslator.translate(text)
                        .addOnSuccessListener { translatedText ->
                            trySend(translatedText)
                        }
                        .addOnFailureListener {
                            trySend("")
                        }
                }
                .addOnFailureListener {
                    trySend("")
                }

            awaitClose()
        }
    }
}