package com.anafthdev.musicompose2.foundation.uicomponent

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
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
import com.anafthdev.musicompose2.feature.theme.circle
import com.anafthdev.musicompose2.foundation.common.ClearRippleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomMusicPlayer(
	currentSong: Song,
	isPlaying: Boolean,
	modifier: Modifier = Modifier,
	onClick: () -> Unit,
	onFavoriteClicked: (Boolean) -> Unit,
	onPlayPauseClicked: (Boolean) -> Unit
) {
	
	Card(
		onClick = onClick,
		shape = MaterialTheme.shapes.extraLarge,
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.primaryContainer
		),
		modifier = modifier
	
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.padding(16.dp)
				.fillMaxWidth()
		) {
			AlbumImage(path = currentSong.albumPath)
			
			Column(
				verticalArrangement = Arrangement.SpaceEvenly,
				modifier = Modifier
					.padding(horizontal = 8.dp)
					.weight(1f)
			) {
				Text(
					text = currentSong.title,
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
				onClick = {
					onFavoriteClicked(!currentSong.isFavorite)
				}
			) {
				Icon(
					painter = painterResource(
						id = if (currentSong.isFavorite) R.drawable.ic_favorite_selected
						else R.drawable.ic_favorite_unselected
					),
					contentDescription = null
				)
			}
			
			Spacer(modifier = Modifier.width(8.dp))
			
			PlayPauseButton(
				isPlaying = isPlaying,
				onClick = {
					onPlayPauseClicked(!isPlaying)
				}
			)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayPauseButton(
	isPlaying: Boolean,
	onClick: () -> Unit
) {
	val interactionSource = remember { MutableInteractionSource() }
	
	Card(
		interactionSource = interactionSource,
		shape = MaterialTheme.shapes.large,
		onClick = onClick,
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.background
		)
	) {
		CompositionLocalProvider(
			LocalRippleTheme provides ClearRippleTheme
		) {
			IconButton(
				onClick = onClick,
				interactionSource = interactionSource
			) {
				Icon(
					painter = painterResource(
						id = if (isPlaying) R.drawable.ic_pause_filled_rounded else R.drawable.ic_play_filled_rounded
					),
					contentDescription = null
				)
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlbumImage(
	path: String
) {
	
	val infiniteTransition = rememberInfiniteTransition()
	val angle by infiniteTransition.animateFloat(
		initialValue = 0f,
		targetValue = 360f,
		animationSpec = infiniteRepeatable(
			animation = tween(
				durationMillis = 5000,
				easing = FastOutSlowInEasing
			)
		)
	)
	
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
			.rotate(angle)
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
