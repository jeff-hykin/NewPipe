package org.schabi.newpipe.database.stream.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Flowable
import org.schabi.newpipe.database.BasicDAO
import org.schabi.newpipe.database.stream.model.StreamDontWatchEntity

@Dao
interface StreamDontWatchDAO : BasicDAO<StreamDontWatchEntity> {

    @Query("SELECT * FROM stream_dont_watch")
    override fun getAll(): Flowable<List<StreamDontWatchEntity>>

    @Query("DELETE FROM stream_dont_watch")
    override fun deleteAll(): Int

    override fun listByService(serviceId: Int): Flowable<List<StreamDontWatchEntity>> {
        throw UnsupportedOperationException()
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun markDontWatch(entity: StreamDontWatchEntity)

    @Query("DELETE FROM stream_dont_watch WHERE stream_id = :streamId")
    fun unmarkDontWatch(streamId: Long): Int

    @Query("SELECT EXISTS(SELECT 1 FROM stream_dont_watch WHERE stream_id = :streamId)")
    fun isDontWatch(streamId: Long): Boolean
}
