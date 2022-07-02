package com.anafthdev.musicompose2.feature.search

import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.feature.search.environment.ISearchEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
	searchEnvironment: ISearchEnvironment
): StatefulViewModel<SearchState, Unit, SearchAction, ISearchEnvironment>(
	SearchState(),
	searchEnvironment
) {
	
	init {
		viewModelScope.launch(environment.dispatcher) {
			environment.getSearchQuery().collect { query ->
				setState {
					copy(
						query = query
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.getArtists().collect { artists ->
				setState {
					copy(
						artists = artists
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.getAlbums().collect { albums ->
				setState {
					copy(
						albums = albums
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.getSongs().collect { songs ->
				setState {
					copy(
						songs = songs
					)
				}
			}
		}
	}
	
	override fun dispatch(action: SearchAction) {
		when (action) {
			is SearchAction.Search -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.search(action.query)
				}
			}
		}
	}
}