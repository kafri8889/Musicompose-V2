package com.anafthdev.musicompose2.feature.playlist.playlist.environment

import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.data.model.Song
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface IPlaylistEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun getSongs(): Flow<List<Song>>
	
	fun getPlaylist(): Flow<Playlist>
	
	suspend fun setPlaylist(playlistID: Int)
	
	suspend fun updatePlaylist(mPlaylist: Playlist)
	
}