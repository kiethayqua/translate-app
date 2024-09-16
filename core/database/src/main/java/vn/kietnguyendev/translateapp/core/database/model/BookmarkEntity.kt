package vn.kietnguyendev.translateapp.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bookmark(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "from_text") val fromText: String?,
    @ColumnInfo(name = "to_text") val toText: String?
)