package com.anafthdev.musicompose2.data.datasource.local

import com.anafthdev.musicompose2.data.datasource.local.db.song.SongDao
import com.anafthdev.musicompose2.data.model.Song
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDatasource @Inject constructor(
	private val songDao: SongDao
) {
	
	fun getAllSong(): Flow<List<Song>> {
		return songDao.getAllSong()
	}
	
	suspend fun updateSongs(vararg song: Song) {
		songDao.update(*song)
	}
	
}