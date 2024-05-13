package vn.kietnguyendev.translateapp.data.local.bookmark

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookmarkRepository @Inject constructor(private val bookmarkDao: BookmarkDao) {
    fun getBookmarks(): Flow<List<Bookmark>> {
        return flow {
            emit(bookmarkDao.getAll())
        }
    }

    fun insertBookmarks(bookmarks: List<Bookmark>) = flow {
        bookmarkDao.insertAll(bookmarks = bookmarks)
        emit(Unit)
    }

    fun removeBookmark(bookmark: Bookmark) = flow {
        bookmarkDao.delete(bookmark = bookmark)
        emit(true)
    }
}