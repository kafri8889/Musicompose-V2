package com.anafthdev.musicompose2.feature.delete_playlist.environment

import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.data.repository.Repository
import com.anafthdev.musicompose2.foundation.di.DiName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Named

class DeletePlaylistEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val repository: Repository
): IDeletePlaylistEnvironment {
	
	private val _playlist = MutableStateFlow(Playlist.default)
	private val playlist: StateFlow<Playlist> = _playlist
	
	override fun getPlaylist(): Flow<Playlist> {
		return playlist
	}
	
	override suspend fun setPlaylist(playlistID: Int) {
		_playlist.emit(
			repository.getPlaylist(playlistID) ?: Playlist.default
		)
	}
	
	override suspend fun deletePlaylist(playlist: Playlist) {
		repository.deletePlaylists(playlist)
	}
	
}