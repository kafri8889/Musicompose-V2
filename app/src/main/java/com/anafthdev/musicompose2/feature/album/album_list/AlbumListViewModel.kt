package com.anafthdev.musicompose2.feature.album.album_list

import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.feature.album.album_list.environment.IAlbumListEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
	albumListEnvironment: IAlbumListEnvironment
): StatefulViewModel<AlbumListState, Unit, Unit, IAlbumListEnvironment>(
	AlbumListState(),
	albumListEnvironment
) {
	
	init {
		viewModelScope.launch(environment.dispatcher) {
			environment.getAllAlbum().collect { albums ->
				setState {
					copy(
						albums = albums
					)
				}
			}
		}
	}
	
	override fun dispatch(action: Unit) {}
	
}