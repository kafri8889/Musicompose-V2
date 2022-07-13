package com.anafthdev.musicompose2.feature.playlist.playlist_sheet.environment

import com.anafthdev.musicompose2.data.model.Playlist
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface IPlaylistSheetEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun getPlaylist(): Flow<Playlist>
	
	suspend fun setPlaylist(playlistID: Int)
	
	suspend fun updatePlaylist(playlist: Playlist)
	
	suspend fun createPlaylist(name: String)
	
}