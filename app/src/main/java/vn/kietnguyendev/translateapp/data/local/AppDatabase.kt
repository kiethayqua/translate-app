package vn.kietnguyendev.translateapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import vn.kietnguyendev.translateapp.data.local.bookmark.Bookmark
import vn.kietnguyendev.translateapp.data.local.bookmark.BookmarkDao

@Database(
    entities = [Bookmark::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}