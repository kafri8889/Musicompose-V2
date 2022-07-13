package com.anafthdev.musicompose2.feature.more_option_music_player_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anafthdev.musicompose2.feature.more_option_music_player_sheet.data.MoreOptionMusicPlayerSheetType
import com.anafthdev.musicompose2.feature.musicompose.LocalMusicomposeState
import com.anafthdev.musicompose2.foundation.uicomponent.MoreOptionItem

@Composable
fun MoreOptionMusicPlayerSheetScreen(
	onAlbumClicked: () -> Unit,
	onArtistClicked: () -> Unit,
	onAddToPlaylist: () -> Unit,
	onSetTimerClicked: () -> Unit
) {
	
	val musicomposeState = LocalMusicomposeState.current
	
	Column(
		modifier = Modifier
			.background(MaterialTheme.colorScheme.surfaceVariant)
			.padding(vertical = 24.dp)
	) {
		MoreOptionMusicPlayerSheetType.values().forEach { type ->
			MoreOptionItem(
				song = musicomposeState.currentSongPlayed,
				type = type,
				onClick = {
					when (type) {
						MoreOptionMusicPlayerSheetType.ALBUM -> onAlbumClicked()
						MoreOptionMusicPlayerSheetType.ARTIST -> onArtistClicked()
						MoreOptionMusicPlayerSheetType.SET_TIMER -> onSetTimerClicked()
						MoreOptionMusicPlayerSheetType.ADD_TO_PLAYLIST -> onAddToPlaylist()
					}
				},
				modifier = Modifier
					.fillMaxWidth()
			)
		}
	}
}
