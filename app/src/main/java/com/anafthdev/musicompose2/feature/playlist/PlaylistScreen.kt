package com.anafthdev.musicompose2.feature.playlist

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.MusicomposeDestination
import com.anafthdev.musicompose2.data.PlaylistOption
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.feature.musicompose.LocalMusicomposeState
import com.anafthdev.musicompose2.foundation.common.LocalSongController
import com.anafthdev.musicompose2.foundation.extension.toDp
import com.anafthdev.musicompose2.foundation.theme.Inter
import com.anafthdev.musicompose2.foundation.uicomponent.BottomMusicPlayerDefault
import com.anafthdev.musicompose2.foundation.uicomponent.SongItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PlaylistScreen(
	playlistID: Int,
	navController: NavController
) {
	
	val context = LocalContext.current
	val density = LocalDensity.current
	val songController = LocalSongController.current
	val musicomposeState = LocalMusicomposeState.current
	
	val viewModel = hiltViewModel<PlaylistViewModel>()
	
	val state by viewModel.state.collectAsState()
	
	val scope = rememberCoroutineScope()
	
	var albumThumbIndex by remember { mutableStateOf(0) }
	var albumImageSize by remember { mutableStateOf(DpSize.Zero) }
	
	val playlistThumb = remember(state.playlist, albumThumbIndex) {
		when {
			state.playlist.icon == R.drawable.ic_playlist_unknown && state.playlist.songs.isNotEmpty() -> {
				state.playlist.songs[albumThumbIndex].albumPath.toUri()
			}
			else -> "android.resource://${context.packageName}/${R.drawable.ic_playlist_unknown}".toUri()
		}
	}
	
	LaunchedEffect(playlistID) {
		viewModel.dispatch(
			PlaylistAction.GetPlaylist(playlistID)
		)
	}
	
	BackHandler {
		navController.popBackStack()
	}
	
	LazyColumn(
		modifier = Modifier
			.statusBarsPadding()
			.fillMaxSize()
	) {
		item {
			Column {
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
					}
				)
				
				Row(
					horizontalArrangement = Arrangement.SpaceBetween,
					modifier = Modifier
						.padding(horizontal = 16.dp)
						.fillMaxWidth()
						.onSizeChanged { size ->
							albumImageSize = DpSize(
								width = size.height.toDp(density),
								height = size.height.toDp(density)
							)
						}
				) {
					
					Image(
						painter = rememberAsyncImagePainter(
							with(ImageRequest.Builder(context)) {
								data(
									data = if (state.playlist.icon != R.drawable.ic_playlist_unknown) state.playlist.icon
									else playlistThumb
								)
								error(R.drawable.ic_playlist_unknown)
								placeholder(R.drawable.ic_playlist_unknown)
								listener(
									onError = { _, _ ->
										if (albumThumbIndex < state.playlist.songs.lastIndex) {
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
							.size(albumImageSize)
							.clip(MaterialTheme.shapes.medium)
					)
					
					Column(
						modifier = Modifier
							.padding(start = 8.dp)
							.weight(1f)
					) {
						
						// Playlist name
						Text(
							maxLines = 1,
							text = state.playlist.name,
							overflow = TextOverflow.Ellipsis,
							style = MaterialTheme.typography.titleMedium.copy(
								fontWeight = FontWeight.Bold,
								fontFamily = Inter
							),
							modifier = Modifier
								.padding(bottom = 8.dp)
						)
						
						if (!state.playlist.isDefault) {
							OutlinedButton(
								shape = MaterialTheme.shapes.medium,
								contentPadding = PaddingValues(0.dp),
								onClick = {
									scope.launch {
										songController?.hideBottomMusicPlayer()
										delay(800)
										navController.navigate(
											MusicomposeDestination.BottomSheet.Playlist.createRoute(
												option = PlaylistOption.EDIT,
												playlistID = state.playlist.id
											)
										)
									}
								},
								modifier = Modifier
									.fillMaxWidth()
							) {
								Row(
									verticalAlignment = Alignment.CenterVertically,
									horizontalArrangement = Arrangement.End,
									modifier = Modifier
										.fillMaxWidth()
								) {
									Icon(
										imageVector = Icons.Rounded.Edit,
										contentDescription = null,
										modifier = Modifier
											.padding(8.dp)
											.weight(0.5f, fill = false)
									)
									
									Text(
										text = stringResource(id = R.string.edit),
										style = MaterialTheme.typography.bodyMedium.copy(
											fontWeight = FontWeight.Bold,
											fontFamily = Inter
										),
										modifier = Modifier
											.weight(0.5f)
									)
								}
							}
							
							Button(
								shape = MaterialTheme.shapes.medium,
								contentPadding = PaddingValues(0.dp),
								onClick = {
									scope.launch {
										songController?.hideBottomMusicPlayer()
										delay(800)
										navController.navigate(
											MusicomposeDestination.BottomSheet.DeletePlaylist.createRoute(
												playlistID = state.playlist.id
											)
										)
									}
								},
								modifier = Modifier
									.fillMaxWidth()
									.padding(top = 4.dp)
							) {
								Row(
									verticalAlignment = Alignment.CenterVertically,
									horizontalArrangement = Arrangement.End,
									modifier = Modifier
										.fillMaxWidth()
								) {
									Icon(
										imageVector = Icons.Rounded.Delete,
										contentDescription = null,
										modifier = Modifier
											.padding(8.dp)
											.weight(0.5f, fill = false)
									)
									
									Text(
										text = stringResource(id = R.string.delete),
										textAlign = TextAlign.Start,
										style = MaterialTheme.typography.bodyMedium.copy(
											fontWeight = FontWeight.Bold,
											fontFamily = Inter
										),
										modifier = Modifier
											.weight(0.5f)
									)
								}
							}
						} else {
							Box(
								modifier = Modifier
									.height(
										ButtonDefaults.MinHeight
											.plus(16.dp)
											.times(2)
									)
							)
						}
					}  // Playlist name, button edit, button delete
					
				}
			}
		}
		
		items(
			items = state.playlist.songs,
			key = { song: Song -> song.hashCode() }
		) { song ->
			SongItem(
				song = song,
				showAlbumImage = false,
				isMusicPlaying = musicomposeState.currentSongPlayed.audioID == song.audioID,
				onClick = {
					songController?.play(song)
				},
				onFavoriteClicked = { isFavorite ->
					songController?.updateSong(
						song.copy(
							isFavorite = isFavorite
						)
					)
				}
			)
		}
		
		// BottomMusicPlayer padding
		item {
			Spacer(modifier = Modifier.height(BottomMusicPlayerDefault.Height))
		}
	}
}
