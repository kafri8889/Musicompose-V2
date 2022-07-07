package com.anafthdev.musicompose2.feature.music_player_sheet

import com.anafthdev.musicompose2.feature.music_player_sheet.environment.IMusicPlayerSheetEnvironment
import com.anafthdev.musicompose2.foundation.viewmodel.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicPlayerSheetViewModel @Inject constructor(
	musicPlayerSheetEnvironment: IMusicPlayerSheetEnvironment
): StatefulViewModel<MusicPlayerSheetState, Unit, Unit, IMusicPlayerSheetEnvironment>(
	MusicPlayerSheetState,
	musicPlayerSheetEnvironment
) {
	
	override fun dispatch(action: Unit) {
	
	}
}