package com.anafthdev.musicompose2.data.repository

import com.anafthdev.musicompose2.data.datasource.local.LocalDatasource
import com.anafthdev.musicompose2.data.model.Playlist
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
	
	suspend fun updateSongs(vararg song: Song) {
		localDatasource.updateSongs(*song)
	}
	
	suspend fun insertSongs(vararg song: Song) {
		localDatasource.insertSongs(*song)
	}
	
	
	
	fun getPlaylists(): Flow<List<Playlist>> {
		return localDatasource.getAllPlaylist()
	}
	
	fun getPlaylist(id: Int): Playlist? {
		return localDatasource.getPlaylist(id)
	}
	
	suspend fun deletePlaylists(vararg playlist: Playlist) {
		localDatasource.updatePlaylists(*playlist)
	}
	
	suspend fun updatePlaylists(vararg playlist: Playlist) {
		localDatasource.updatePlaylists(*playlist)
	}
	
	suspend fun insertPlaylists(vararg playlist: Playlist) {
		localDatasource.insertPlaylists(*playlist)
	}
	
}