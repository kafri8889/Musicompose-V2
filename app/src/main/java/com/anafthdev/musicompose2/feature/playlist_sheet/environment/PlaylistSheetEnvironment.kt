package com.anafthdev.musicompose2.feature.playlist_sheet.environment

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
	
	private val _playlistName = MutableStateFlow("")
	private val playlistName: StateFlow<String> = _playlistName
	
	override fun getPlaylistName(): Flow<String> {
		return playlistName
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