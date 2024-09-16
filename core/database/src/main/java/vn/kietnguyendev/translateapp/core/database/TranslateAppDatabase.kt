package vn.kietnguyendev.translateapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import vn.kietnguyendev.translateapp.core.database.dao.BookmarkDao
import vn.kietnguyendev.translateapp.core.database.model.Bookmark

@Database(
    entities = [Bookmark::class],
    version = 1
)
abstract class TranslateAppDatabase: RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}