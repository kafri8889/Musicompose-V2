package com.anafthdev.musicompose2.feature.playlist.delete_playlist

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.MusicomposeDestination
import com.anafthdev.musicompose2.foundation.theme.Inter
import com.anafthdev.musicompose2.foundation.theme.circle

@Composable
fun DeletePlaylistScreen(
	playlistID: Int,
	navController: NavController
) {

	val viewModel = hiltViewModel<DeletePlaylistViewModel>()
	
	val state by viewModel.state.collectAsState()
	
	LaunchedEffect(playlistID) {
		viewModel.dispatch(
			DeletePlaylistAction.GetPlaylist(playlistID)
		)
	}
	
	BackHandler {
		navController.popBackStack()
	}
	
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = Modifier
			.fillMaxWidth()
	) {
		Divider(
			thickness = 4.dp,
			modifier = Modifier
				.padding(8.dp)
				.fillMaxWidth(0.2f)
				.clip(circle)
				.align(Alignment.CenterHorizontally)
		)
		
		Text(
			maxLines = 1,
			text = stringResource(id = R.string.delete),
			overflow = TextOverflow.Ellipsis,
			style = MaterialTheme.typography.titleMedium.copy(
				fontWeight = FontWeight.Bold
			),
			modifier = Modifier
				.padding(8.dp)
		)
		
		Divider(
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 8.dp)
		)
		
		Button(
			onClick = {
				viewModel.dispatch(
					DeletePlaylistAction.DeletePlaylist(state.playlist)
				)
				
				navController.popBackStack(
					route = MusicomposeDestination.Main.route,
					inclusive = false
				)
			},
			modifier = Modifier
				.padding(
					vertical = 4.dp,
					horizontal = 16.dp
				)
				.fillMaxWidth()
		) {
			Text(
				text = stringResource(id = R.string.ok),
				style = MaterialTheme.typography.bodyMedium.copy(
					fontWeight = FontWeight.Bold,
					fontFamily = Inter
				)
			)
		}
		
		TextButton(
			onClick = {
				navController.popBackStack()
			},
			modifier = Modifier
				.padding(
					vertical = 4.dp,
					horizontal = 16.dp
				)
				.fillMaxWidth()
		) {
			Text(
				text = stringResource(id = R.string.cancel),
				style = MaterialTheme.typography.bodyMedium.copy(
					fontWeight = FontWeight.Bold,
					fontFamily = Inter
				)
			)
		}
	}
}
