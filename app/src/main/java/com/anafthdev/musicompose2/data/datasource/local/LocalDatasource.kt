package com.anafthdev.musicompose2.data.datasource.local

import com.anafthdev.musicompose2.data.datasource.local.db.playlist.PlaylistDao
import com.anafthdev.musicompose2.data.datasource.local.db.song.SongDao
import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.data.model.Song
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDatasource @Inject constructor(
	private val songDao: SongDao,
	private val playlistDao: PlaylistDao
) {
	
	fun getAllSong(): Flow<List<Song>> {
		return songDao.getAllSong()
	}
	
	fun getSong(audioID: Long): Song? {
		return songDao.get(audioID)
	}
	
	suspend fun updateSongs(vararg song: Song) {
		songDao.update(*song)
	}
	
	suspend fun insertSongs(vararg song: Song) {
		songDao.insert(*song)
	}
	
	
	
	fun getAllPlaylist(): Flow<List<Playlist>> {
		return playlistDao.getAllPlaylist()
	}
	
	fun getPlaylist(id: Int): Playlist? {
		return playlistDao.get(id)
	}
	
	suspend fun deletePlaylists(vararg playlist: Playlist) {
		playlistDao.delete(*playlist)
	}
	
	suspend fun updatePlaylists(vararg playlist: Playlist) {
		playlistDao.update(*playlist)
	}
	
	suspend fun insertPlaylists(vararg playlist: Playlist) {
		playlistDao.insert(*playlist)
	}
	
}