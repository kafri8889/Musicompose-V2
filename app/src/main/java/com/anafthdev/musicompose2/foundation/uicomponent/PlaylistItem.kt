package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.foundation.theme.Inter
import com.anafthdev.musicompose2.foundation.theme.no_shape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistItem(
	playlist: Playlist,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	val context = LocalContext.current
	
	var albumThumbIndex by remember { mutableStateOf(0) }
	
	val playlistThumb = remember(playlist, albumThumbIndex) {
		when {
			playlist.icon == R.drawable.ic_playlist_unknown && playlist.songs.isNotEmpty() -> {
				playlist.songs[albumThumbIndex].albumPath.toUri()
			}
			else -> "android.resource://${context.packageName}/${R.drawable.ic_playlist_unknown}".toUri()
		}
	}
	
	Card(
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.background
		),
		shape = no_shape,
		onClick = onClick,
		modifier = modifier
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.fillMaxWidth()
				.padding(8.dp)
				.height(72.dp)
		) {
			
			Image(
				painter = rememberAsyncImagePainter(
					with(ImageRequest.Builder(context)) {
						data(
							data = if (playlist.icon != R.drawable.ic_playlist_unknown) playlist.icon
							else playlistThumb
						)
						error(R.drawable.ic_playlist_unknown)
						placeholder(R.drawable.ic_playlist_unknown)
						listener(
							onError = { _, _ ->
								if (albumThumbIndex < playlist.songs.lastIndex) {
									albumThumbIndex += 1
									data(playlistThumb)
								}
							}
						)
						
						build()
					}
				),
				contentDescription = null,
				modifier = Modifier
					.size(56.dp)
					.clip(MaterialTheme.shapes.medium)
			)
			
			Column(
				verticalArrangement = Arrangement.SpaceEvenly,
				modifier = Modifier
					.padding(horizontal = 8.dp)
					.height(56.dp)
			) {
				Text(
					text = playlist.name,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					style = MaterialTheme.typography.titleSmall.copy(
						fontWeight = FontWeight.SemiBold
					)
				)
				
				Text(
					text = "${playlist.songs.size} ${stringResource(id = R.string.song)}",
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					style = MaterialTheme.typography.bodySmall.copy(
						color = LocalContentColor.current.copy(alpha = 0.7f),
						fontFamily = Inter
					)
				)
			}
			
			Spacer(modifier = Modifier.weight(1f))
			
			Icon(
				imageVector = Icons.Rounded.KeyboardArrowRight,
				contentDescription = null,
				modifier = Modifier
					.padding(end = 8.dp)
					.size(24.dp)
			)
			
		}
	}
}
