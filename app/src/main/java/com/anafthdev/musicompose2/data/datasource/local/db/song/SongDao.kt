package com.anafthdev.musicompose2.data.datasource.local.db.song

import androidx.room.*
import com.anafthdev.musicompose2.data.model.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
	
	@Query("SELECT * FROM song_table")
	fun getAllSong(): Flow<List<Song>>
	
	@Query("SELECT * FROM song_table WHERE isFavorite=1")
	fun getFavoriteSong(): Flow<List<Song>>
	
	@Query("SELECT * FROM song_table WHERE audioID LIKE :mAudioID")
	fun get(mAudioID: Long): Song?
	
	@Update
	suspend fun update(vararg song: Song)
	
	@Delete
	suspend fun delete(vararg song: Song)
	
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insert(vararg song: Song)
	
}