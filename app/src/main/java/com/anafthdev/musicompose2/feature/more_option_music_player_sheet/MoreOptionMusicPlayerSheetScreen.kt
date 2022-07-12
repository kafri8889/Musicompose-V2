package com.anafthdev.musicompose2.feature.more_option_music_player_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.anafthdev.musicompose2.data.MusicomposeDestination
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.feature.more_option_music_player_sheet.data.MoreOptionMusicPlayerSheetType
import com.anafthdev.musicompose2.feature.musicompose.LocalMusicomposeState
import com.anafthdev.musicompose2.foundation.extension.getIcon
import com.anafthdev.musicompose2.foundation.extension.getLabel
import com.anafthdev.musicompose2.foundation.theme.Inter

@Composable
fun MoreOptionMusicPlayerSheetScreen(
	navController: NavController
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
						MoreOptionMusicPlayerSheetType.ALBUM -> navController.navigate(
							MusicomposeDestination.Album.createRoute(
								musicomposeState.currentSongPlayed.albumID
							)
						)
						MoreOptionMusicPlayerSheetType.ARTIST -> navController.navigate(
							MusicomposeDestination.Artist.createRoute(
								musicomposeState.currentSongPlayed.artistID
							)
						)
						MoreOptionMusicPlayerSheetType.SET_TIMER -> {
							// TODO: SetTimerScreen
						}
						MoreOptionMusicPlayerSheetType.ADD_TO_PLAYLIST -> {
							// TODO: Add to playlist
						}
					}
				},
				modifier = Modifier
					.fillMaxWidth()
			)
		}
	}
}

@Composable
fun MoreOptionItem(
	song: Song,
	type: MoreOptionMusicPlayerSheetType,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier
			.clickable { onClick() }
			.then(modifier)
	) {
		Icon(
			painter = painterResource(id = type.getIcon()),
			contentDescription = null,
			modifier = Modifier
				.padding(16.dp)
		)
		
		Text(
			maxLines = 1,
			overflow = TextOverflow.Ellipsis,
			text = type.getLabel(
				when (type) {
					MoreOptionMusicPlayerSheetType.ALBUM -> song.album
					MoreOptionMusicPlayerSheetType.ARTIST -> song.artist
					else -> ""
				}
			),
			style = MaterialTheme.typography.titleSmall.copy(
				fontFamily = Inter
			),
			modifier = Modifier
				.padding(end = 24.dp)
		)
	}
}
