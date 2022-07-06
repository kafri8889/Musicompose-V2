package com.anafthdev.musicompose2.feature.playlist.delete_playlist.environment

import com.anafthdev.musicompose2.data.model.Playlist
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface IDeletePlaylistEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun getPlaylist(): Flow<Playlist>
	
	suspend fun setPlaylist(playlistID: Int)
	
	suspend fun deletePlaylist(playlist: Playlist)
	
}