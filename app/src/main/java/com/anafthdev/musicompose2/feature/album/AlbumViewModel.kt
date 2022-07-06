package com.anafthdev.musicompose2.feature.album

import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.feature.album.environment.IAlbumEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
	albumEnvironment: IAlbumEnvironment
): StatefulViewModel<AlbumState, Unit, AlbumAction, IAlbumEnvironment>(
	AlbumState(),
	albumEnvironment
) {
	
	init {
		viewModelScope.launch(environment.dispatcher) {
			environment.getAlbum().collect { album ->
				setState {
					copy(
						album = album
					)
				}
			}
		}
	}
	
	override fun dispatch(action: AlbumAction) {
		when (action) {
			is AlbumAction.GetAlbum -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setAlbum(action.id)
				}
			}
			is AlbumAction.UpdateSong -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.updateSong(action.song)
				}
			}
		}
	}
}