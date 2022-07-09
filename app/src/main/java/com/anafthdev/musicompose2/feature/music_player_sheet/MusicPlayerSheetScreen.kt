package com.anafthdev.musicompose2.feature.music_player_sheet

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.PlaybackMode
import com.anafthdev.musicompose2.data.SkipForwardBackward
import com.anafthdev.musicompose2.feature.musicompose.LocalMusicomposeState
import com.anafthdev.musicompose2.feature.musicompose.MusicomposeState
import com.anafthdev.musicompose2.feature.play_queue.PlayQueueScreen
import com.anafthdev.musicompose2.foundation.common.BottomSheetLayoutConfig
import com.anafthdev.musicompose2.foundation.common.LocalSongController
import com.anafthdev.musicompose2.foundation.theme.Inter
import com.anafthdev.musicompose2.foundation.uiextension.currentFraction
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MusicPlayerSheetScreen(
	navController: NavController,
	bottomSheetLayoutConfig: BottomSheetLayoutConfig
) {
	
	val config = LocalConfiguration.current
	val musicomposeState = LocalMusicomposeState.current
	
	val viewModel = hiltViewModel<MusicPlayerSheetViewModel>()
	
	val state by viewModel.state.collectAsState()
	
	val scope = rememberCoroutineScope()
	val scaffoldState = rememberBottomSheetScaffoldState(
		bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
	)
	
	BackHandler {
		navController.popBackStack()
	}
	
	BottomSheetScaffold(
		scaffoldState = scaffoldState,
		sheetContent = {
			// TODO: show play queue
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.fillMaxHeight(
						// add some padding between MotionContent and SheetContent (1f -> 0.99f)
						0.99f.minus(MOTION_CONTENT_HEIGHT.value / config.screenHeightDp)
					)
			) {
				PlayQueueScreen(
					isExpanded = scaffoldState.bottomSheetState.isExpanded,
					onBack = {
						scope.launch {
							scaffoldState.bottomSheetState.collapse()
						}
					}
				)
			}
		},
		modifier = Modifier
			.systemBarsPadding()
			.fillMaxSize()
	) {
		MotionContent(
			musicomposeState = musicomposeState,
			fraction = scaffoldState.currentFraction,
			background = bottomSheetLayoutConfig.sheetBackgroundColor,
			modifier = Modifier
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlbumImage(
	albumPath: String,
	modifier: Modifier = Modifier
) {
	
	Card(
		shape = MaterialTheme.shapes.large,
		elevation = CardDefaults.cardElevation(
			defaultElevation = 8.dp
		),
		modifier = modifier
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

@Composable
fun OtherButtons(
	musicomposeState: MusicomposeState,
	modifier: Modifier = Modifier,
	onPlaybackModeClicked: () -> Unit,
	onFavoriteClicked: () -> Unit,
	onBackwardClicked: () -> Unit,
	onForwardClicked: () -> Unit,
	onShuffleClicked: () -> Unit
) {
	
	Row(
		horizontalArrangement = Arrangement.SpaceEvenly,
		verticalAlignment = Alignment.CenterVertically,
		modifier = modifier
	) {
		IconButton(
			onClick = onBackwardClicked
		) {
			Icon(
				painter = painterResource(
					id = when (musicomposeState.skipForwardBackward) {
						SkipForwardBackward.FIVE_SECOND -> R.drawable.ic_backward_5_sec
						SkipForwardBackward.TEN_SECOND -> R.drawable.ic_backward_10_sec
						SkipForwardBackward.FIFTEEN_SECOND -> R.drawable.ic_backward_15_sec
					}
				),
				contentDescription = null
			)
		}
		
		IconButton(
			colors = IconButtonDefaults.iconButtonColors(
				contentColor = IconButtonDefaults.iconButtonColors().contentColor(
					enabled = musicomposeState.playbackMode != PlaybackMode.REPEAT_OFF
				).value
			),
			onClick = onPlaybackModeClicked
		) {
			Icon(
				painter = painterResource(
					id = when (musicomposeState.playbackMode) {
						PlaybackMode.REPEAT_ONE -> R.drawable.ic_repeate_one
						PlaybackMode.REPEAT_ALL -> R.drawable.ic_repeate_on
						PlaybackMode.REPEAT_OFF -> R.drawable.ic_repeate_on
					}
				),
				contentDescription = null
			)
		}
		
		IconButton(
			onClick = onFavoriteClicked
		) {
			Image(
				painter = painterResource(
					id = if (musicomposeState.currentSongPlayed.isFavorite) R.drawable.ic_favorite_selected
					else R.drawable.ic_favorite_unselected
				),
				contentDescription = null
			)
		}
		
		IconButton(
			colors = IconButtonDefaults.iconButtonColors(
				contentColor = IconButtonDefaults.iconButtonColors().contentColor(
					enabled = musicomposeState.isShuffled
				).value
			),
			onClick = onShuffleClicked
		) {
			Icon(
				painter = painterResource(id = R.drawable.ic_shuffle),
				contentDescription = null
			)
		}
		
		IconButton(
			onClick = onForwardClicked
		) {
			Icon(
				painter = painterResource(
					id = when (musicomposeState.skipForwardBackward) {
						SkipForwardBackward.FIVE_SECOND -> R.drawable.ic_forward_5_sec
						SkipForwardBackward.TEN_SECOND -> R.drawable.ic_forward_10_sec
						SkipForwardBackward.FIFTEEN_SECOND -> R.drawable.ic_forward_15_sec
					}
				),
				contentDescription = null
			)
		}
	}
}

@OptIn(ExperimentalMotionApi::class)
@Composable
private fun MotionContent(
	fraction: Float,
	background: Color,
	musicomposeState: MusicomposeState,
	modifier: Modifier = Modifier
) {
	
	val context = LocalContext.current
	val songController = LocalSongController.current
	
	val motionScene = remember {
		context.resources
			.openRawResource(R.raw.motion_scene)
			.readBytes()
			.decodeToString()
	}
	
	Row(
		modifier = modifier
			.background(background)
			.fillMaxSize()
	) {
		MotionLayout(
			motionScene = MotionScene(content = motionScene),
			progress = fraction,
			modifier = Modifier
				.fillMaxWidth()
		) {
			
			Spacer(modifier = Modifier.layoutId("top_bar"))
			
			AlbumImage(
				albumPath = musicomposeState.currentSongPlayed.albumPath,
				modifier = Modifier
					.layoutId("album_image")
					.fillMaxWidth(0.8f)
					.aspectRatio(1f, true)
			)
			
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.SpaceEvenly,
				modifier = Modifier
					.layoutId("column_title_artist")
			) {
				AnimatedVisibility(visible = fraction < 0.8f) {
					Spacer(modifier = Modifier.height(16.dp))
				}
				
				Text(
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					text = musicomposeState.currentSongPlayed.title,
					textAlign = if (fraction > 0.8f) TextAlign.Start else TextAlign.Center,
					style = MaterialTheme.typography.titleLarge.copy(
						fontWeight = FontWeight.Bold,
						fontSize = if (fraction > 0.8f) MaterialTheme.typography.titleMedium.fontSize
						else MaterialTheme.typography.titleLarge.fontSize
					),
					modifier = Modifier
						.fillMaxWidth(if (fraction > 0.8f) 1f else 0.7f)
				)
				
				Text(
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					text = musicomposeState.currentSongPlayed.artist,
					textAlign = if (fraction > 0.8f) TextAlign.Start else TextAlign.Center,
					style = MaterialTheme.typography.titleMedium.copy(
						fontFamily = Inter,
						fontSize = if (fraction > 0.8f) MaterialTheme.typography.titleSmall.fontSize
						else MaterialTheme.typography.titleMedium.fontSize
					),
					modifier = Modifier
						.fillMaxWidth(if (fraction > 0.8) 1f else 0.7f)
				)
			}
			
			Row(
				modifier = Modifier
					.layoutId("row_buttons")
			) {
				IconButton(
					onClick = {
						if (musicomposeState.isPlaying) songController?.pause()
						else songController?.resume()
					}
				) {
					Icon(
						painter = painterResource(
							id = if (!musicomposeState.isPlaying) R.drawable.ic_play_filled_rounded else R.drawable.ic_pause_filled_rounded
						),
						contentDescription = null
					)
				}
				
				IconButton(
					onClick = {
						songController?.next()
					}
				) {
					Icon(
						painter = painterResource(id = R.drawable.ic_next_filled_rounded),
						contentDescription = null
					)
				}
			}
			
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier = Modifier
					.layoutId("column_other")
			) {
				Spacer(modifier = Modifier.height(24.dp))
				
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
				
				Spacer(modifier = Modifier.height(16.dp))
				
				OtherButtons(
					musicomposeState = musicomposeState,
					onPlaybackModeClicked = {
						songController?.changePlaybackMode()
					},
					onFavoriteClicked = {
						songController?.updateSong(
							musicomposeState.currentSongPlayed.copy(
								isFavorite = !musicomposeState.currentSongPlayed.isFavorite
							)
						)
					},
					onBackwardClicked = {
						songController?.backward()
					},
					onForwardClicked = {
						songController?.forward()
					},
					onShuffleClicked = {
						songController?.setShuffled(!musicomposeState.isShuffled)
					},
					modifier = Modifier
						.fillMaxWidth()
				)
			}
		}
	}
	
}

private val MOTION_CONTENT_HEIGHT = 64.dp
