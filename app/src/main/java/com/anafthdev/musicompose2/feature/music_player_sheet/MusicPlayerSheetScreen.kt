package com.anafthdev.musicompose2.feature.music_player_sheet

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
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
import androidx.compose.ui.res.stringResource
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
import com.anafthdev.musicompose2.data.MusicomposeDestination
import com.anafthdev.musicompose2.data.PlaybackMode
import com.anafthdev.musicompose2.data.SkipForwardBackward
import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.feature.more_option_music_player_sheet.MoreOptionMusicPlayerSheetScreen
import com.anafthdev.musicompose2.feature.more_option_music_player_sheet.data.MoreOptionMusicPlayerSheetType
import com.anafthdev.musicompose2.feature.musicompose.LocalMusicomposeState
import com.anafthdev.musicompose2.feature.musicompose.MusicomposeState
import com.anafthdev.musicompose2.feature.play_queue.PlayQueueScreen
import com.anafthdev.musicompose2.foundation.common.BottomSheetLayoutConfig
import com.anafthdev.musicompose2.foundation.common.LocalSongController
import com.anafthdev.musicompose2.foundation.extension.isAddToPlaylist
import com.anafthdev.musicompose2.foundation.extension.isSetTimer
import com.anafthdev.musicompose2.foundation.extension.toast
import com.anafthdev.musicompose2.foundation.theme.Inter
import com.anafthdev.musicompose2.foundation.uicomponent.MoreOptionPlaylistItem
import com.anafthdev.musicompose2.foundation.uiextension.currentFraction
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MusicPlayerSheetScreen(
	navController: NavController,
	bottomSheetLayoutConfig: BottomSheetLayoutConfig
) {
	
	val context = LocalContext.current
	val musicomposeState = LocalMusicomposeState.current
	
	val viewModel = hiltViewModel<MusicPlayerSheetViewModel>()
	
	val state by viewModel.state.collectAsState()
	
	val scope = rememberCoroutineScope()
	val scaffoldState = rememberBottomSheetScaffoldState(
		bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
	)
	
	val moreOptionSheetState = rememberModalBottomSheetState(
		initialValue = ModalBottomSheetValue.Hidden,
		skipHalfExpanded = true
	)

	var moreOptionType by remember { mutableStateOf(MoreOptionMusicPlayerSheetType.ALBUM) }
	
	BackHandler {
		when {
			scaffoldState.bottomSheetState.isExpanded -> scope.launch {
				scaffoldState.bottomSheetState.collapse()
			}
			moreOptionType.isAddToPlaylist() || moreOptionType.isSetTimer() -> {
				moreOptionType = MoreOptionMusicPlayerSheetType.ALBUM
			}
			moreOptionSheetState.isVisible -> scope.launch {
				moreOptionSheetState.hide()
			}
			else -> navController.popBackStack()
		}
	}
	
	MoreOptionSheet(
		type = moreOptionType,
		state = state,
		sheetState = moreOptionSheetState,
		onBack = {
			moreOptionType = MoreOptionMusicPlayerSheetType.ALBUM
		},
		onAlbumClicked = {
			navController.navigate(
				MusicomposeDestination.Album.createRoute(
					musicomposeState.currentSongPlayed.albumID
				)
			)
		},
		onArtistClicked = {
			navController.navigate(
				MusicomposeDestination.Artist.createRoute(
					musicomposeState.currentSongPlayed.artistID
				)
			)
		},
		onAddToPlaylist = {
			moreOptionType = MoreOptionMusicPlayerSheetType.ADD_TO_PLAYLIST
		},
		onSetTimerClicked = {
		
		},
		onPlaylistClicked = { playlist ->
			val contain = playlist.songs.contains(musicomposeState.currentSongPlayed.audioID)
			
			if (!contain) {
				viewModel.dispatch(
					MusicPlayerSheetAction.AddToPlaylist(
						song = musicomposeState.currentSongPlayed,
						playlist = playlist
					)
				)
				
				context.getString(R.string.added_to_playlist).toast(context, Toast.LENGTH_LONG)
			} else context.getString(R.string.added).toast(context, Toast.LENGTH_LONG)
			
			scope.launch {
				moreOptionSheetState.hide()
				moreOptionType = MoreOptionMusicPlayerSheetType.ALBUM
			}
		}
	) {
		PlayQueueSheet(
			state = scaffoldState
		) {
			MotionContent(
				musicomposeState = musicomposeState,
				fraction = scaffoldState.currentFraction,
				background = bottomSheetLayoutConfig.sheetBackgroundColor,
				onMoreClicked = {
					scope.launch {
						moreOptionSheetState.show()
					}
				}
			)
		}
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
	modifier: Modifier = Modifier,
	onMoreClicked: () -> Unit
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
			
			Row(
				horizontalArrangement = Arrangement.End,
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.fillMaxWidth()
					.layoutId("top_app_bar_content")
			) {
				IconButton(
					onClick = onMoreClicked
				) {
					Icon(
						imageVector = Icons.Rounded.MoreVert,
						contentDescription = null
					)
				}
			}
			
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoreOptionSheet(
	state: MusicPlayerSheetState,
	sheetState: ModalBottomSheetState,
	type: MoreOptionMusicPlayerSheetType,
	onBack: () -> Unit,
	onAlbumClicked: () -> Unit,
	onArtistClicked: () -> Unit,
	onAddToPlaylist: () -> Unit,
	onSetTimerClicked: () -> Unit,
	onPlaylistClicked: (Playlist) -> Unit,
	content: @Composable () -> Unit
) {
	
	ModalBottomSheetLayout(
		sheetState = sheetState,
		sheetShape = MaterialTheme.shapes.large.copy(
			bottomEnd = CornerSize(0),
			bottomStart = CornerSize(0)
		),
		sheetContent = {
			when (type) {
				MoreOptionMusicPlayerSheetType.ADD_TO_PLAYLIST -> {
					AddToPlaylistSheetScreen(
						playlists = state.playlists,
						onBack = onBack,
						onPlaylistClicked = onPlaylistClicked
					)
				}
				MoreOptionMusicPlayerSheetType.SET_TIMER -> {
				
				}
				else -> {
					MoreOptionMusicPlayerSheetScreen(
						onAlbumClicked = onAlbumClicked,
						onArtistClicked = onArtistClicked,
						onAddToPlaylist = onAddToPlaylist,
						onSetTimerClicked = onSetTimerClicked
					)
				}
			}
		},
		modifier = Modifier
			.fillMaxSize()
	) {
		content()
	}
}

@Composable
fun AddToPlaylistSheetScreen(
	playlists: List<Playlist>,
	onBack: () -> Unit,
	onPlaylistClicked: (Playlist) -> Unit
) {

	Column(
		modifier = Modifier
			.background(MaterialTheme.colorScheme.surfaceVariant)
			.fillMaxWidth()
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.fillMaxWidth()
		) {
			IconButton(
				onClick = onBack,
				modifier = Modifier
					.padding(16.dp)
			) {
				Icon(
					imageVector = Icons.Rounded.ArrowBack,
					contentDescription = null,
				)
			}
			
			Text(
				maxLines = 1,
				overflow = TextOverflow.Ellipsis,
				text = stringResource(id = R.string.add_to_playlist),
				style = MaterialTheme.typography.titleSmall.copy(
					fontFamily = Inter
				),
				modifier = Modifier
					.padding(end = 24.dp)
			)
		}
		
		LazyColumn(
			modifier = Modifier
				.fillMaxWidth()
		) {
			items(
				items = playlists,
				key = { item: Playlist -> item.hashCode() }
			) { playlist ->
				MoreOptionPlaylistItem(
					playlist = playlist,
					onClick = {
						onPlaylistClicked(playlist)
					}
				)
			}
			
			item {
				Spacer(modifier = Modifier.height(24.dp))
			}
		}
	}
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayQueueSheet(
	state: BottomSheetScaffoldState,
	content: @Composable () -> Unit
) {
	
	val config = LocalConfiguration.current
	
	val scope = rememberCoroutineScope()
	
	BottomSheetScaffold(
		scaffoldState = state,
		sheetContent = {
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.fillMaxHeight(
						// add some padding between MotionContent and SheetContent (1f -> 0.99f)
						0.99f.minus(MOTION_CONTENT_HEIGHT.value / config.screenHeightDp)
					)
			) {
				PlayQueueScreen(
					isExpanded = state.bottomSheetState.isExpanded,
					onBack = {
						scope.launch {
							state.bottomSheetState.collapse()
						}
					}
				)
			}
		},
		modifier = Modifier
			.systemBarsPadding()
			.fillMaxSize()
	) {
		content()
	}
}

private val MOTION_CONTENT_HEIGHT = 64.dp
