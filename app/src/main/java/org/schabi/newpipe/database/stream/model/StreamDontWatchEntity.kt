package org.schabi.newpipe.database.stream.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(
    tableName = StreamDontWatchEntity.TABLE_NAME,
    primaryKeys = [StreamDontWatchEntity.STREAM_ID],
    foreignKeys = [
        ForeignKey(
            entity = StreamEntity::class,
            parentColumns = [StreamEntity.STREAM_ID],
            childColumns = [StreamDontWatchEntity.STREAM_ID],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
data class StreamDontWatchEntity(
    @ColumnInfo(name = STREAM_ID)
    val streamUid: Long
) {
    companion object {
        const val TABLE_NAME = "stream_dont_watch"
        const val STREAM_ID = "stream_id"
    }
}
