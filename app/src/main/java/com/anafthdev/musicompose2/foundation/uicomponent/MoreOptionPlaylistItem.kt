package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.foundation.theme.Inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreOptionPlaylistItem(
	playlist: Playlist,
	onClick: () -> Unit,
	modifier: Modifier = Modifier
) {

	Card(
		colors = CardDefaults.cardColors(
			containerColor = Color.Transparent
		),
		onClick = onClick,
		modifier = modifier
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.fillMaxWidth()
		) {
			Icon(
				painter = painterResource(
					id = when (playlist.id) {
						Playlist.favorite.id -> R.drawable.ic_favorite_border
						Playlist.justPlayed.id -> R.drawable.ic_clock
						else -> R.drawable.ic_music_playlist
					}
				),
				contentDescription = null,
				modifier = Modifier
					.padding(16.dp)
			)
			
			Text(
				maxLines = 1,
				overflow = TextOverflow.Ellipsis,
				text = playlist.name,
				style = MaterialTheme.typography.titleSmall.copy(
					fontFamily = Inter
				),
				modifier = Modifier
					.padding(end = 24.dp)
			)
		}
	}
}
