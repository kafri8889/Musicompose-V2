package com.anafthdev.musicompose2.feature.playlist_list.environment

import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.data.repository.Repository
import com.anafthdev.musicompose2.foundation.di.DiName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class PlaylistListEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val repository: Repository
): IPlaylistListEnvironment {
	
	override fun getPlaylists(): Flow<List<Playlist>> {
		return repository.getAllPlaylist()
	}
	
	override suspend fun newPlaylist(playlist: Playlist) {
		repository.insertPlaylists(playlist)
	}
	
	
}