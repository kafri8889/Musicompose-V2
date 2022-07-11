package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DragHandle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.foundation.theme.Inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongQueueItem(
	song: Song,
	selected: Boolean,
	modifier: Modifier = Modifier,
	elevation: Dp = 0.dp,
	onClick: () -> Unit
) {
	
	Card(
		onClick = onClick,
		elevation = CardDefaults.cardElevation(
			draggedElevation = elevation
		),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surfaceVariant
		),
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
					ImageRequest.Builder(LocalContext.current)
						.data(song.albumPath.toUri())
						.error(R.drawable.ic_music_unknown)
						.placeholder(R.drawable.ic_music_unknown)
						.build()
				),
				contentDescription = null,
				modifier = Modifier
					.padding(4.dp)
					.fillMaxHeight()
					.aspectRatio(1f)
					.clip(MaterialTheme.shapes.medium)
			)
			
			Column(
				verticalArrangement = Arrangement.SpaceEvenly,
				modifier = Modifier
					.padding(horizontal = 8.dp)
					.weight(1f)
			) {
				Text(
					text = song.title,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					style = MaterialTheme.typography.bodyMedium.copy(
						color = if (selected) MaterialTheme.colorScheme.primary
						else LocalContentColor.current,
						fontWeight = FontWeight.Bold
					)
				)
				
				Text(
					text = "${song.artist} • ${song.album}",
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					style = MaterialTheme.typography.bodySmall.copy(
						color = if (selected) MaterialTheme.colorScheme.primary
						else LocalContentColor.current.copy(alpha = 0.7f),
						fontFamily = Inter
					)
				)
			}
			
			Box(
				contentAlignment = Alignment.Center,
				modifier = Modifier
					.size(56.dp)
			) {
				Icon(
					imageVector = Icons.Rounded.DragHandle,
					contentDescription = null
				)
			}
		}
	}
}
