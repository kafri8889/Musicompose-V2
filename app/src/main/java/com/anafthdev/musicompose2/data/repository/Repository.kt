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
	
}