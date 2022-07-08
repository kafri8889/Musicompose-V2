package com.anafthdev.musicompose2.feature.music_player_sheet

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.feature.musicompose.LocalMusicomposeState
import com.anafthdev.musicompose2.foundation.common.LocalSongController
import com.anafthdev.musicompose2.foundation.theme.Inter
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun MusicPlayerSheetScreen(
	navController: NavController
) {
	
	val songController = LocalSongController.current
	val musicomposeState = LocalMusicomposeState.current
	
	val viewModel = hiltViewModel<MusicPlayerSheetViewModel>()
	
	val state by viewModel.state.collectAsState()

	BackHandler {
		navController.popBackStack()
	}
	
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = Modifier
			.statusBarsPadding()
			.fillMaxSize()
	) {
		SmallTopAppBar(
			colors = TopAppBarDefaults.smallTopAppBarColors(
				containerColor = Color.Transparent
			),
			title = {},
			navigationIcon = {
				IconButton(
					onClick = {
						navController.popBackStack()
					}
				) {
					Icon(
						imageVector = Icons.Rounded.ArrowBack,
						contentDescription = null
					)
				}
			},
			actions = {
				IconButton(
					onClick = {
						songController?.updateSong(
							musicomposeState.currentSongPlayed.copy(
								isFavorite = !musicomposeState.currentSongPlayed.isFavorite
							)
						)
					}
				) {
					Image(
						painter = painterResource(
							id = if (musicomposeState.currentSongPlayed.isFavorite) R.drawable.ic_favorite_selected
							else R.drawable.ic_favorite_unselected
						),
						contentDescription = null
					)
				}
			}
		)
		
		AlbumImage(albumPath = musicomposeState.currentSongPlayed.albumPath)
		
		Spacer(modifier = Modifier.height(16.dp))
		
		Text(
			text = musicomposeState.currentSongPlayed.title,
			style = MaterialTheme.typography.titleLarge.copy(
				fontWeight = FontWeight.Bold
			)
		)
		
		Spacer(modifier = Modifier.height(16.dp))
		
		Text(
			text = musicomposeState.currentSongPlayed.artist,
			style = MaterialTheme.typography.titleMedium.copy(
				fontFamily = Inter
			)
		)
		
		Spacer(modifier = Modifier.height(16.dp))
		
		SongProgress(
			maxDuration = musicomposeState.currentSongPlayed.duration,
			currentDuration = musicomposeState.currentDuration,
			onChange = { progress ->
				val duration = progress * musicomposeState.currentSongPlayed.duration
				
				songController?.snapTo(duration.toLong())
			}
		)
		
		Spacer(modifier = Modifier.height(16.dp))
		
		SongControlButtons(
			isPlaying = musicomposeState.isPlaying,
			onPrevious = {
				songController?.previous()
			},
			onPlayPause = {
				if (musicomposeState.isPlaying) songController?.pause()
				else songController?.resume()
			},
			onNext = {
				songController?.next()
			}
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlbumImage(
	albumPath: String
) {
	
	Card(
		shape = MaterialTheme.shapes.large,
		elevation = CardDefaults.cardElevation(
			defaultElevation = 8.dp
		),
		modifier = Modifier
			.padding(
				vertical = 16.dp
			)
			.fillMaxWidth(0.7f)
			.aspectRatio(1f)
	) {
		Image(
			painter = rememberAsyncImagePainter(
				ImageRequest.Builder(LocalContext.current)
					.data(albumPath.toUri())
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

@Composable
fun SongProgress(
	maxDuration: Long,
	currentDuration: Long,
	onChange: (Float) -> Unit
) {
	
	val progress = remember(maxDuration, currentDuration) {
		currentDuration.toFloat() / maxDuration.toFloat()
	}
	
	val maxDurationInMinute = remember(maxDuration) {
		maxDuration.milliseconds.inWholeMinutes
	}
	
	val maxDurationInSecond = remember(maxDuration) {
		maxDuration.milliseconds.inWholeSeconds % 60
	}
	
	val currentDurationInMinute = remember(currentDuration) {
		currentDuration.milliseconds.inWholeMinutes
	}
	
	val currentDurationInSecond = remember(currentDuration) {
		currentDuration.milliseconds.inWholeSeconds % 60
	}
	
	val maxDurationString = remember(maxDurationInMinute, maxDurationInSecond) {
		val minute = if (maxDurationInMinute < 10) "0$maxDurationInMinute"
		else maxDurationInMinute.toString()
		
		val second = if (maxDurationInSecond < 10) "0$maxDurationInSecond"
		else maxDurationInSecond.toString()
		
		return@remember "$minute:$second"
	}
	
	val currentDurationString = remember(currentDurationInMinute, currentDurationInSecond) {
		val minute = if (currentDurationInMinute < 10) "0$currentDurationInMinute"
		else currentDurationInMinute.toString()
		
		val second = if (currentDurationInSecond < 10) "0$currentDurationInSecond"
		else currentDurationInSecond.toString()
		
		return@remember "$minute:$second"
	}
	
	Column(
		modifier = Modifier
			.fillMaxWidth(0.8f)
	) {
		Slider(
			value = progress,
			onValueChange = onChange,
		)
		
		Spacer(modifier = Modifier.height(8.dp))
		
		Row(
			horizontalArrangement = Arrangement.SpaceBetween,
			modifier = Modifier
				.fillMaxWidth()
		) {
			Text(
				text = currentDurationString,
				style = MaterialTheme.typography.titleMedium.copy(
					fontFamily = Inter
				)
			)
			
			Text(
				text = maxDurationString,
				style = MaterialTheme.typography.titleMedium.copy(
					fontFamily = Inter
				)
			)
		}
	}
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongControlButtons(
	isPlaying: Boolean,
	onPrevious: () -> Unit,
	onPlayPause: () -> Unit,
	onNext: () -> Unit
) {
	
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceEvenly,
		modifier = Modifier
			.fillMaxWidth(0.8f)
	) {
		IconButton(
			onClick = onPrevious
		) {
			Icon(
				painter = painterResource(id = R.drawable.ic_previous_filled_rounded),
				contentDescription = null
			)
		}
		
		Card(
			colors = CardDefaults.cardColors(
				containerColor = MaterialTheme.colorScheme.primaryContainer
			),
			shape = MaterialTheme.shapes.large,
			onClick = onPlayPause,
			modifier = Modifier
				.size(64.dp)
		) {
			Box(
				contentAlignment = Alignment.Center,
				modifier = Modifier
					.fillMaxSize()
			) {
				Icon(
					painter = painterResource(
						id = if (!isPlaying) R.drawable.ic_play_filled_rounded else R.drawable.ic_pause_filled_rounded
					),
					contentDescription = null
				)
			}
		}
		
		IconButton(
			onClick = onNext
		) {
			Icon(
				painter = painterResource(id = R.drawable.ic_next_filled_rounded),
				contentDescription = null
			)
		}
	}
}
