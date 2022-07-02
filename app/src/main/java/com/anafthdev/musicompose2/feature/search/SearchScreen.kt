package com.anafthdev.musicompose2.feature.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.MusicomposeDestination
import com.anafthdev.musicompose2.data.model.Album
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.feature.musicompose.LocalMusicomposeState
import com.anafthdev.musicompose2.feature.theme.Inter
import com.anafthdev.musicompose2.foundation.common.LocalSongController
import com.anafthdev.musicompose2.foundation.uicomponent.AlbumItem
import com.anafthdev.musicompose2.foundation.uicomponent.SongItem

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
	navController: NavController
) {
	
	val focusManager = LocalFocusManager.current
	val songController = LocalSongController.current
	val musicomposeState = LocalMusicomposeState.current
	val keyboardController = LocalSoftwareKeyboardController.current
	
	val viewModel = hiltViewModel<SearchViewModel>()
	
	val currentRoute = navController.currentBackStackEntry?.destination?.route
	
	val state by viewModel.state.collectAsState()
	
	val searchTextFieldFocusRequester = remember { FocusRequester() }
	
	LaunchedEffect(currentRoute) {
		if (currentRoute == MusicomposeDestination.Search.route) {
			searchTextFieldFocusRequester.requestFocus()
		}
	}
	
	BackHandler {
		navController.popBackStack()
	}
	
	Column(
		modifier = Modifier
			.systemBarsPadding()
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.background)
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.fillMaxWidth()
		) {
			TextField(
				value = state.query,
				singleLine = true,
				onValueChange = { s ->
					viewModel.dispatch(
						SearchAction.Search(s)
					)
				},
				trailingIcon = {
					if (state.query.isNotBlank()) {
						IconButton(
							onClick = {
								viewModel.dispatch(
									SearchAction.Search("")
								)
							}
						) {
							Image(
								painter = painterResource(id = R.drawable.x_mark_outlined_filled),
								contentDescription = null,
								modifier = Modifier
									.size(16.dp)
							)
						}
					}
				},
				placeholder = {
					Text(
						text = stringResource(id = R.string.search_placeholder),
						style = LocalTextStyle.current.copy(
							fontFamily = Inter
						)
					)
				},
				keyboardOptions = KeyboardOptions(
					imeAction = ImeAction.Done
				),
				keyboardActions = KeyboardActions(
					onDone = {
						keyboardController?.hide()
						focusManager.clearFocus()
					}
				),
				colors = TextFieldDefaults.textFieldColors(
					containerColor = Color.Transparent,
					focusedIndicatorColor = Color.Transparent,
					unfocusedIndicatorColor = Color.Transparent
				),
				modifier = Modifier
					.weight(0.5f)
					.padding(end = 8.dp)
					.focusRequester(searchTextFieldFocusRequester)
			)
			
			Divider(
				color = MaterialTheme.colorScheme.onBackground,
				modifier = Modifier
					.weight(0.01f, fill = false)
					.size(1.dp, 16.dp)
			)
			
			TextButton(
				onClick = {
					navController.popBackStack()
				},
				modifier = Modifier
					.weight(0.18f)
					.padding(start = 8.dp)
			) {
				Text(
					text = stringResource(id = R.string.cancel),
					style = MaterialTheme.typography.bodySmall.copy(
						fontFamily = Inter,
						fontWeight = FontWeight.Bold
					)
				)
			}
		}
		
		Divider(
			color = MaterialTheme.colorScheme.onBackground,
			thickness = 1.dp,
			modifier = Modifier
				.fillMaxWidth()
		)
		
		LazyColumn(
			modifier = Modifier
				.padding(bottom = 64.dp)
		) {
			
			item {
				if (state.songs.isNotEmpty()) {
					Box(
						modifier = Modifier
							.fillMaxWidth()
					) {
						Text(
							text = stringResource(id = R.string.song),
							style = MaterialTheme.typography.bodyLarge.copy(
								fontFamily = Inter,
								fontWeight = FontWeight.Bold,
							),
							modifier = Modifier
								.align(Alignment.CenterStart)
								.padding(start = 14.dp, top = 16.dp, bottom = 8.dp)
						)
						
						Text(
							text = "(${state.songs.size})",
							style = MaterialTheme.typography.bodyMedium.copy(
								color = LocalContentColor.current.copy(alpha = 0.6f),
								fontFamily = Inter
							),
							modifier = Modifier
								.align(Alignment.CenterEnd)
								.padding(end = 14.dp, top = 16.dp, bottom = 16.dp)
						)
					}
				}
			}
			
			items(
				items = state.songs,
				key = { song: Song -> song.audioID }
			) { song ->
				SongItem(
					song = song,
					isMusicPlaying = musicomposeState.currentSongPlayed.audioID == song.audioID,
					onClick = {
						songController?.play(song)
					},
					modifier = Modifier
						.padding(vertical = 4.dp)
				)
			}
			
			item {
				if (state.artists.isNotEmpty()) {
					if (state.songs.isNotEmpty()) {
						Divider(
							color = MaterialTheme.colorScheme.onBackground,
							thickness = 1.dp,
							modifier = Modifier
								.fillMaxWidth()
								.padding(vertical = 24.dp)
						)
					}
					
					Box(
						modifier = Modifier
							.fillMaxWidth()
					) {
						Text(
							text = stringResource(id = R.string.artist),
							style = MaterialTheme.typography.bodyLarge.copy(
								fontFamily = Inter,
								fontWeight = FontWeight.Bold,
							),
							modifier = Modifier
								.align(Alignment.CenterStart)
								.padding(start = 14.dp, top = 16.dp, bottom = 8.dp)
						)
						
						Text(
							text = "(${state.artists.size})",
							style = MaterialTheme.typography.bodyMedium.copy(
								color = LocalContentColor.current.copy(alpha = 0.6f),
								fontFamily = Inter
							),
							modifier = Modifier
								.align(Alignment.CenterEnd)
								.padding(end = 14.dp, top = 16.dp, bottom = 16.dp)
						)
					}
				}
			}
			
			items(state.artists) { artist ->
				Row(
					verticalAlignment = Alignment.CenterVertically,
					modifier = Modifier
						.fillMaxWidth()
						.padding(16.dp)
				) {
					Text(
						maxLines = 1,
						text = artist.name,
						overflow = TextOverflow.Ellipsis,
						style = MaterialTheme.typography.bodyLarge.copy(
							fontWeight = FontWeight.SemiBold
						),
						modifier = Modifier
							.padding(end = 8.dp)
							.weight(1f)
					)
					
					IconButton(
						onClick = {
							// TODO: to ArtistScreen
						}
					) {
						Icon(
							imageVector = Icons.Rounded.KeyboardArrowRight,
							contentDescription = null
						)
					}
				}
			}
			
			item {
				if (state.albums.isNotEmpty()) {
					if (state.songs.isNotEmpty() and state.artists.isNotEmpty()) {
						Divider(
							color = MaterialTheme.colorScheme.onBackground,
							thickness = 1.dp,
							modifier = Modifier
								.fillMaxWidth()
								.padding(vertical = 24.dp)
						)
					}
					
					Box(
						modifier = Modifier
							.fillMaxWidth()
					) {
						Text(
							text = stringResource(id = R.string.album),
							style = MaterialTheme.typography.bodyLarge.copy(
								fontFamily = Inter,
								fontWeight = FontWeight.Bold,
							),
							modifier = Modifier
								.align(Alignment.CenterStart)
								.padding(start = 14.dp, top = 16.dp, bottom = 8.dp)
						)
						
						Text(
							text = "(${state.albums.size})",
							style = MaterialTheme.typography.bodyMedium.copy(
								color = LocalContentColor.current.copy(alpha = 0.6f),
								fontFamily = Inter
							),
							modifier = Modifier
								.align(Alignment.CenterEnd)
								.padding(end = 14.dp, top = 16.dp, bottom = 16.dp)
						)
					}
				}
			}
			
			items(
				items = state.albums,
				key = { album: Album -> album.id }
			) { album ->
				AlbumItem(
					album = album,
					onClick = {
						// TODO: to AlbumScreen
					}
				)
			}
			
			item {
				Spacer(
					modifier = Modifier
						.fillMaxWidth()
						.height(32.dp)
				)
			}
			
		}
	}
	
}
