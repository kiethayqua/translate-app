package vn.kietnguyendev.translateapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import vn.kietnguyendev.translateapp.core.database.model.Bookmark

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmark")
    fun getAll(): List<Bookmark>

    @Insert
    fun insertAll(bookmarks: List<Bookmark>)

    @Delete
    fun delete(bookmark: Bookmark)
}