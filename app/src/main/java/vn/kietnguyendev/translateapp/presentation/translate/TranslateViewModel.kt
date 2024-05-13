package vn.kietnguyendev.translateapp.presentation.translate

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
import javax.inject.Inject

@HiltViewModel
class TranslateViewModel @Inject constructor(private val bookmarkRepository: BookmarkRepository): ViewModel() {
    private val _state = mutableStateOf(TranslateState(from = "English", to = "Vietnamese"))
    val state: State<TranslateState> = _state

    fun initWithText(initialText: String) {
        if (initialText.isNotEmpty()) {
            _state.value = _state.value.copy(fromText = initialText)
            onTranslate(initialText)
        }
    }

    fun onChangeText(text: String) {
        _state.value = _state.value.copy(fromText = text)
    }

    fun onTranslate(text: String) {
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

    private fun translateFlow(text: String): Flow<String> {
        return callbackFlow {
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.VIETNAMESE)
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