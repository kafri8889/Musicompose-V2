package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.model.Artist
import com.anafthdev.musicompose2.feature.theme.Inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistItem(
	artist: Artist,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {

	Card(
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.background
		),
		onClick = onClick,
		modifier = modifier
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(8.dp)
				.height(72.dp)
		) {
			AlbumImage(artist = artist)
			
			Column(
				verticalArrangement = Arrangement.SpaceEvenly,
				modifier = Modifier
					.padding(start = 8.dp)
					.fillMaxHeight()
			) {
				Text(
					text = artist.name,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					style = MaterialTheme.typography.bodyMedium.copy(
						color = LocalContentColor.current,
						fontWeight = FontWeight.Bold
					)
				)
				
				Text(
					text = stringResource(
						id = R.string.n_song,
						artist.songs.size
					),
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

@Composable
private fun AlbumImage(
	artist: Artist
) {
	
	val albumSize = artist.albums.size.coerceAtMost(3)
	
	Box(
		contentAlignment = Alignment.CenterStart,
		modifier = Modifier
			.size(72.dp)
	) {
		for (i in 1..albumSize) {
			Image(
				painter = rememberAsyncImagePainter(
					ImageRequest.Builder(LocalContext.current)
						.data(artist.albums[i - 1].songs[0].albumPath.toUri())
						.error(R.drawable.ic_music_unknown)
						.placeholder(R.drawable.ic_music_unknown)
						.build()
				),
				contentDescription = null,
				modifier = Modifier
					.padding(
						start = 6.times(i).dp,
						top = 8.dp,
						bottom = 8.dp
					)
					.size(56.dp)
					.clip(MaterialTheme.shapes.medium)
					.zIndex(albumSize.minus(i).toFloat())
			)
		}
	}
}
