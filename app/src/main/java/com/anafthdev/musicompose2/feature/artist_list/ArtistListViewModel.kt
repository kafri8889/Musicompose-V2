package com.anafthdev.musicompose2.feature.artist_list

import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.feature.artist_list.environment.IArtistListEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistListViewModel @Inject constructor(
	artistListEnvironment: IArtistListEnvironment
): StatefulViewModel<ArtistListState, Unit, Unit, IArtistListEnvironment>(
	ArtistListState(),
	artistListEnvironment
) {
	
	init {
		viewModelScope.launch(environment.dispatcher) {
			environment.getAllArtist().collect { artists ->
				setState {
					copy(
						artists = artists
					)
				}
			}
		}
	}
	
	override fun dispatch(action: Unit) {}
}