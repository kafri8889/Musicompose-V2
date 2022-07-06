package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.model.Album
import com.anafthdev.musicompose2.foundation.theme.Inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumItem(
	album: Album,
	modifier: Modifier = Modifier,
	containerColor: Color = MaterialTheme.colorScheme.background,
	onClick: () -> Unit
) {

	Card(
		colors = CardDefaults.cardColors(
			containerColor = containerColor
		),
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
					ImageRequest.Builder(LocalContext.current)
						.data(
							if (album.songs.isNotEmpty()) album.songs[0].albumPath.toUri()
							else R.drawable.ic_music_unknown
						)
						.error(R.drawable.ic_music_unknown)
						.placeholder(R.drawable.ic_music_unknown)
						.build()
				),
				contentDescription = null,
				modifier = Modifier
					.padding(8.dp)
					.fillMaxHeight()
					.aspectRatio(1f)
					.clip(MaterialTheme.shapes.medium)
			)
			
			Column(
				verticalArrangement = Arrangement.SpaceEvenly,
				modifier = Modifier
					.padding(end = 8.dp)
					.weight(1f)
			) {
				Text(
					text = album.name,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					style = MaterialTheme.typography.bodyMedium.copy(
						color = LocalContentColor.current,
						fontWeight = FontWeight.Bold
					)
				)
				
				Text(
					text = album.artist,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					style = MaterialTheme.typography.bodySmall.copy(
						color = LocalContentColor.current.copy(alpha = 0.7f),
						fontFamily = Inter
					)
				)
			}
		}
	}
}
