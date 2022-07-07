package com.anafthdev.musicompose2.feature.song_selector

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.SongSelectorType
import com.anafthdev.musicompose2.data.model.Song
import com.anafthdev.musicompose2.foundation.extension.isSelected
import com.anafthdev.musicompose2.foundation.theme.Inter
import com.anafthdev.musicompose2.foundation.uicomponent.BottomMusicPlayerDefault
import com.anafthdev.musicompose2.foundation.uicomponent.BottomMusicPlayerImpl
import com.anafthdev.musicompose2.foundation.uicomponent.SongSelectorItem

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SongSelectorScreen(
	type: SongSelectorType,
	playlistID: Int,
	navController: NavController
) {
	
	val focusManager = LocalFocusManager.current
	val keyboardController = LocalSoftwareKeyboardController.current
	
	val viewModel = hiltViewModel<SongSelectorViewModel>()
	
	val state by viewModel.state.collectAsState()
	
	val searchTextFieldFocusRequester = remember { FocusRequester() }
	
	LaunchedEffect(playlistID) {
		viewModel.dispatch(
			SongSelectorAction.GetPlaylist(playlistID)
		)
	}
	
	LaunchedEffect(type) {
		viewModel.dispatch(
			SongSelectorAction.SetSongSelectorType(type)
		)
	}
	
	Box(
		modifier = Modifier
			.statusBarsPadding()
			.fillMaxSize()
	) {
		Column {
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
							SongSelectorAction.Search(s)
						)
					},
					trailingIcon = {
						if (state.query.isNotBlank()) {
							IconButton(
								onClick = {
									viewModel.dispatch(
										SongSelectorAction.Search("")
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
			
			LazyColumn {
				
				items(
					items = state.songs,
					key = { song: Song -> song.audioID }
				) { song ->
					
					val contain = remember(song, state.selectedSong, state.songs) {
						song in state.selectedSong
					}
					
					SongSelectorItem(
						song = song,
						contain = contain,
						selected = song.isSelected(),
						type = type,
						onClick = {
							viewModel.dispatch(
								if (contain) SongSelectorAction.RemoveSong(song)
								else SongSelectorAction.AddSong(song)
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
		
		BottomMusicPlayerImpl(navController = navController)
	}
	
}
