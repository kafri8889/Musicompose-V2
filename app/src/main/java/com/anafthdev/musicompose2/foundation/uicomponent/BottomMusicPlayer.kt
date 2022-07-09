package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.annotation.FloatRange
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.foundation.theme.circle
import com.anafthdev.musicompose2.foundation.theme.no_shape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomMusicPlayer(
	currentSong: Song,
	currentDuration: Long,
	isPlaying: Boolean,
	modifier: Modifier = Modifier,
	onClick: () -> Unit,
	onFavoriteClicked: () -> Unit,
	onPlayPauseClicked: (Boolean) -> Unit
) {
	
	val progress = remember(currentDuration, currentSong.duration) {
		currentDuration.toFloat() / currentSong.duration.toFloat()
	}
	
	Card(
		onClick = onClick,
		shape = no_shape,
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.primaryContainer
		),
		modifier = modifier
			.height(BottomMusicPlayerDefault.Height)
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.padding(16.dp)
				.fillMaxWidth()
		) {
			AlbumImage(
				isPlaying = isPlaying,
				path = currentSong.albumPath
			)
			
			Column(
				verticalArrangement = Arrangement.SpaceEvenly,
				modifier = Modifier
					.padding(horizontal = 8.dp)
					.weight(1f)
			) {
				Text(
					maxLines = 2,
					text = currentSong.title,
					overflow = TextOverflow.Ellipsis,
					style = MaterialTheme.typography.titleSmall.copy(
						fontWeight = FontWeight.SemiBold
					)
				)
				
				Spacer(modifier = Modifier.height(8.dp))
				
				Text(
					maxLines = 2,
					overflow = TextOverflow.Ellipsis,
					text = currentSong.artist,
					style = MaterialTheme.typography.bodySmall,
				)
			}
			
			IconButton(
				onClick = onFavoriteClicked
			) {
				Image(
					painter = painterResource(
						id = if (currentSong.isFavorite) R.drawable.ic_favorite_selected
						else R.drawable.ic_favorite_unselected
					),
					contentDescription = null
				)
			}
			
			Spacer(modifier = Modifier.width(8.dp))
			
			PlayPauseButton(
				progress = progress,
				isPlaying = isPlaying,
				onClick = {
					onPlayPauseClicked(!isPlaying)
				}
			)
		}
	}
}

@Composable
private fun PlayPauseButton(
	progress: Float,
	isPlaying: Boolean,
	onClick: () -> Unit
) {
	Box(
		contentAlignment = Alignment.Center,
		modifier = Modifier
			.clip(circle)
			.clickable { onClick() }
	) {
		
		SongProgress(progress = progress)
		
		Icon(
			painter = painterResource(
				id = if (!isPlaying) R.drawable.ic_play_filled_rounded else R.drawable.ic_pause_filled_rounded
			),
			contentDescription = null
		)
	}
}

@Composable
private fun SongProgress(@FloatRange(from = 0.0, to = 1.0) progress: Float) {
	
	val backgroundColor = MaterialTheme.colorScheme.background
	val primaryColor = MaterialTheme.colorScheme.primary
	
	val stroke = with(LocalDensity.current) {
		Stroke(
			width = 4.dp.toPx(),
			cap = StrokeCap.Round
		)
	}
	
	Canvas(
		modifier = Modifier
			.size(56.dp)
	) {
		
		val startAngle = 270f
		val sweepAngle = progress * 360f
		
		val diameterOffset = stroke.width / 2
		val arcDimen = size.width - 2 * diameterOffset
		
		// Progress background
		drawArc(
			color = backgroundColor,
			startAngle = startAngle,
			sweepAngle = 360f,
			useCenter = false,
			topLeft = Offset(diameterOffset, diameterOffset),
			size = Size(arcDimen, arcDimen),
			style = stroke
		)
		
		// Progress
		drawArc(
			color = primaryColor,
			startAngle = startAngle,
			sweepAngle = sweepAngle,
			useCenter = false,
			topLeft = Offset(diameterOffset, diameterOffset),
			size = Size(arcDimen, arcDimen),
			style = stroke
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlbumImage(
	isPlaying: Boolean,
	path: String
) {
	
	val angle = LocalBottomMusicPlayerAlbumImageAngle.current
	
	var currentAngle by remember { mutableStateOf(0f) }

	LaunchedEffect(angle, isPlaying) {
		if (isPlaying) {
			currentAngle = angle
		}
	}
	
	Card(
		shape = circle,
		border = BorderStroke(
			width = 2.dp,
			color = MaterialTheme.colorScheme.onSurface
		),
		elevation = CardDefaults.cardElevation(
			defaultElevation = 8.dp
		),
		modifier = Modifier
			.size(56.dp)
			.rotate(currentAngle)
	) {
		Box(modifier = Modifier.fillMaxSize()) {
			Box(
				modifier = Modifier
					.size(12.dp)
					.clip(circle)
					.background(MaterialTheme.colorScheme.primaryContainer)
					.border(
						width = 2.dp,
						color = MaterialTheme.colorScheme.onSurface,
						shape = circle
					)
					.align(Alignment.Center)
					.zIndex(2f)
			)
			
			Image(
				painter = rememberAsyncImagePainter(
					ImageRequest.Builder(LocalContext.current)
						.data(path.toUri())
						.error(R.drawable.ic_music_unknown)
						.placeholder(R.drawable.ic_music_unknown)
						.build()
				),
				contentDescription = null,
				modifier = Modifier
					.fillMaxSize()
			)
		}
	}
}

object BottomMusicPlayerDefault {
	
	val Height = 96.dp
	
}

val LocalBottomMusicPlayerAlbumImageAngle = compositionLocalOf { 0f }
