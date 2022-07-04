package com.anafthdev.musicompose2.data.repository

import com.anafthdev.musicompose2.data.datasource.local.LocalDatasource
import com.anafthdev.musicompose2.data.model.Song
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
	private val localDatasource: LocalDatasource
) {
	
	fun getSongs(): Flow<List<Song>> {
		return localDatasource.getAllSong()
	}
	
	fun getLocalSong(audioID: Long): Song? {
		return localDatasource.getSong(audioID)
	}
	
	suspend fun updateLocalSongs(vararg song: Song) {
		localDatasource.updateSongs(*song)
	}
	
}