package com.anafthdev.musicompose2.feature.playlist.playlist_list.environment

import com.anafthdev.musicompose2.data.model.Playlist
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface IPlaylistListEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun getPlaylists(): Flow<List<Playlist>>
	
	suspend fun newPlaylist(playlist: Playlist)
	
}