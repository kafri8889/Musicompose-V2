package com.anafthdev.musicompose2.feature.playlist.playlist_sheet.environment

import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.data.repository.Repository
import com.anafthdev.musicompose2.foundation.di.DiName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Named
import kotlin.random.Random

class PlaylistSheetEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val repository: Repository
): IPlaylistSheetEnvironment {
	
	private val _playlist = MutableStateFlow(Playlist.default)
	private val playlist: StateFlow<Playlist> = _playlist
	
	private val _playlistName = MutableStateFlow("")
	private val playlistName: StateFlow<String> = _playlistName
	
	override fun getPlaylist(): Flow<Playlist> {
		return playlist
	}
	
	override fun getPlaylistName(): Flow<String> {
		return playlistName
	}
	
	override suspend fun setPlaylist(playlistID: Int) {
		_playlist.emit(
			repository.getPlaylist(playlistID) ?: Playlist.default
		)
	}
	
	override suspend fun updatePlaylist(playlist: Playlist) {
		repository.updatePlaylists(playlist)
	}
	
	override suspend fun setPlaylistName(name: String) {
		_playlistName.emit(name)
	}
	
	override suspend fun createPlaylist() {
		repository.insertPlaylists(
			Playlist(
				id = Random.nextInt(),
				icon = R.drawable.ic_playlist_unknown,
				name = playlistName.value,
				songs = emptyList()
			)
		)
	}
	
}