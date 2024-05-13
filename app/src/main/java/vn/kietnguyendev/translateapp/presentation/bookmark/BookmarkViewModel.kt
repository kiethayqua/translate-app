package vn.kietnguyendev.translateapp.presentation.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import vn.kietnguyendev.translateapp.data.local.bookmark.Bookmark
import vn.kietnguyendev.translateapp.data.local.bookmark.BookmarkRepository
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(private val bookmarkRepository: BookmarkRepository) :
    ViewModel() {
    private val _state = mutableStateOf(BookmarkState())
    val state: State<BookmarkState> = _state

    init {
        fetchBookmarks()
    }

    fun onRemoveBookmark(item: BookmarkItem) {
        viewModelScope.launch {
            bookmarkRepository.removeBookmark(
                Bookmark(
                    id = item.id,
                    fromText = item.fromText,
                    toText = item.toText
                )
            ).flowOn(Dispatchers.IO).collect {
                if (it) fetchBookmarks()
            }
        }
    }

    private fun fetchBookmarks() {
        viewModelScope.launch {
            bookmarkRepository.getBookmarks()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    println("KIET_DEBUG_fetch_all_bookmarks_fail: ${e.message}")
                }
                .collect {
                    val bookmarkItems = it.map { bookmark ->
                        BookmarkItem(
                            id = bookmark.id,
                            fromText = bookmark.fromText ?: "",
                            toText = bookmark.toText ?: ""
                        )
                    }
                    _state.value = _state.value.copy(data = bookmarkItems)
                }
        }
    }
}