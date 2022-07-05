package com.anafthdev.musicompose2.feature.playlist_sheet

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.PlaylistOption
import com.anafthdev.musicompose2.foundation.theme.Inter
import com.anafthdev.musicompose2.foundation.theme.circle

@OptIn(
	ExperimentalAnimationApi::class,
	ExperimentalComposeUiApi::class
)
@Composable
fun PlaylistSheetScreen(
	option: PlaylistOption,
	navController: NavController
) {
	
	val focusManager = LocalFocusManager.current
	val keyboardController = LocalSoftwareKeyboardController.current
	
	val viewModel = hiltViewModel<PlaylistSheetViewModel>()
	
	val state by viewModel.state.collectAsState()
	
	val playlistNameFocusRequester = remember { FocusRequester() }
	
	LaunchedEffect(Unit) {
		viewModel.dispatch(PlaylistSheetAction.ChangePlaylistName("dps"))
		playlistNameFocusRequester.requestFocus()
	}
	
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp)
	) {
		Box(
			modifier = Modifier
				.padding(vertical = 8.dp)
				.fillMaxWidth(0.2f)
				.height(2.dp)
				.clip(RoundedCornerShape(100))
				.background(Color.White.copy(alpha = 0.2f))
				.align(Alignment.CenterHorizontally)
		)
		
		Box(
			modifier = Modifier
				.padding(top = 24.dp)
				.fillMaxWidth()
		) {
			IconButton(
				onClick = {
					navController.popBackStack()
				},
				modifier = Modifier
					.align(Alignment.CenterStart)
			) {
				Icon(
					imageVector = Icons.Rounded.Close,
					contentDescription = null
				)
			}
			
			Text(
				text = stringResource(
					id = when (option) {
						PlaylistOption.NEW -> R.string.new_playlist
						PlaylistOption.EDIT -> R.string.rename_playlist
					}
				),
				maxLines = 1,
				overflow = TextOverflow.Ellipsis,
				textAlign = TextAlign.Center,
				style = MaterialTheme.typography.titleSmall.copy(
					fontWeight = FontWeight.Bold
				),
				modifier = Modifier
					.padding(horizontal = 16.dp)
					.align(Alignment.Center)
			)
			
			androidx.compose.animation.AnimatedVisibility(
				visible = state.playlistName.isNotBlank(),
				enter = scaleIn(
					animationSpec = tween(400)
				),
				exit = scaleOut(
					animationSpec = tween(400)
				),
				modifier = Modifier
					.align(Alignment.CenterEnd)
			) {
				IconButton(
					onClick = {
						viewModel.dispatch(
							PlaylistSheetAction.CreatePlaylist
						)
						
						navController.popBackStack()
					}
				) {
					Icon(
						imageVector = Icons.Rounded.Check,
						contentDescription = null,
					)
				}
			}
		}
		
		OutlinedTextField(
			singleLine = true,
			shape = circle,
			value = state.playlistName,
			textStyle = LocalTextStyle.current.copy(
				fontFamily = Inter
			),
			keyboardOptions = KeyboardOptions(
				imeAction = ImeAction.Done
			),
			keyboardActions = KeyboardActions(
				onDone = {
					focusManager.clearFocus(force = true)
					keyboardController?.hide()
				}
			),
			onValueChange = { s ->
				if (state.playlistName.length < 25) viewModel.dispatch(
					PlaylistSheetAction.ChangePlaylistName(s)
				)
			},
			label = {
				Text(
					text = stringResource(id = R.string.enter_playlist_name),
					style = MaterialTheme.typography.bodyMedium
				)
			},
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 16.dp)
				.focusRequester(playlistNameFocusRequester)
		)
		
		Spacer(modifier = Modifier.height(24.dp))
	}
}
