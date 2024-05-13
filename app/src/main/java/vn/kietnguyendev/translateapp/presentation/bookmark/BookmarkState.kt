package vn.kietnguyendev.translateapp.presentation.bookmark

import javax.annotation.concurrent.Immutable

@Immutable
data class BookmarkState(
    val data: List<BookmarkItem> = emptyList()
)

@Immutable
data class BookmarkItem(
    val id: Int,
    val fromText: String,
    val toText: String
)