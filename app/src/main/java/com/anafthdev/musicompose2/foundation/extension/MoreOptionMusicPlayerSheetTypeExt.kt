package com.anafthdev.musicompose2.foundation.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.feature.more_option_music_player_sheet.data.MoreOptionMusicPlayerSheetType

@Composable
fun MoreOptionMusicPlayerSheetType.getLabel(s: String): String {
	return when (this) {
		MoreOptionMusicPlayerSheetType.ALBUM -> stringResource(
			id = R.string.x_album,
			s
		)
		MoreOptionMusicPlayerSheetType.ARTIST -> stringResource(
			id = R.string.x_artist,
			s
		)
		MoreOptionMusicPlayerSheetType.SET_TIMER -> stringResource(id = R.string.set_timer)
		MoreOptionMusicPlayerSheetType.ADD_TO_PLAYLIST -> stringResource(id = R.string.add_to_playlist)
	}
}

@Composable
fun MoreOptionMusicPlayerSheetType.getIcon(): Int {
	return when (this) {
		MoreOptionMusicPlayerSheetType.ALBUM -> R.drawable.ic_cd
		MoreOptionMusicPlayerSheetType.ARTIST -> R.drawable.ic_profile
		MoreOptionMusicPlayerSheetType.SET_TIMER -> R.drawable.ic_timer
		MoreOptionMusicPlayerSheetType.ADD_TO_PLAYLIST -> R.drawable.ic_music_playlist
	}
}
