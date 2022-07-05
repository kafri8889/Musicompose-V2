package com.anafthdev.musicompose2.feature.sort_sheet

import androidx.lifecycle.viewModelScope
import com.anafthdev.musicompose2.feature.sort_sheet.environment.ISortSheetEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SortSheetViewModel @Inject constructor(
	sortSheetEnvironment: ISortSheetEnvironment
): StatefulViewModel<SortSheetState, Unit, SortSheetAction, ISortSheetEnvironment>(
	SortSheetState(),
	sortSheetEnvironment
) {
	
	init {
		viewModelScope.launch(environment.dispatcher) {
			environment.getSortSongOption().collect { option ->
				setState {
					copy(
						sortSongOption = option
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.getSortAlbumOption().collect { option ->
				setState {
					copy(
						sortAlbumOption = option
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.getSortArtistOption().collect { option ->
				setState {
					copy(
						sortArtistOption = option
					)
				}
			}
		}
		
		viewModelScope.launch(environment.dispatcher) {
			environment.getSortPlaylistOption().collect { option ->
				setState {
					copy(
						sortPlaylistOption = option
					)
				}
			}
		}
	}
	
	override fun dispatch(action: SortSheetAction) {
		when (action) {
			is SortSheetAction.SetSortSongOption -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setSortSongOption(action.option)
				}
			}
			is SortSheetAction.SetSortAlbumOption -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setSortAlbumOption(action.option)
				}
			}
			is SortSheetAction.SetSortArtistOption -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setSortArtistOption(action.option)
				}
			}
			is SortSheetAction.SetSortPlaylistOption -> {
				viewModelScope.launch(environment.dispatcher) {
					environment.setSortPlaylistOption(action.option)
				}
			}
		}
	}
}