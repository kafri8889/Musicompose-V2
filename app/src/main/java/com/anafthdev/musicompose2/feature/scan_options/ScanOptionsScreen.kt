package com.anafthdev.musicompose2.feature.scan_options

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.ScanOptions
import com.anafthdev.musicompose2.foundation.uicomponent.ScanOptionsItem

@Composable
fun ScanOptionsScreen(
	navController: NavController
) {
	
	val viewModel = hiltViewModel<ScanOptionsViewModel>()
	
	val state by viewModel.state.collectAsState()
	
	BackHandler {
		navController.popBackStack()
	}
	
	LazyColumn(
		modifier = Modifier
			.systemBarsPadding()
			.fillMaxSize()
	) {
		item {
			SmallTopAppBar(
				colors = TopAppBarDefaults.smallTopAppBarColors(
					containerColor = MaterialTheme.colorScheme.background
				),
				title = {
					Text(
						text = stringResource(id = R.string.scan_options),
						style = MaterialTheme.typography.titleLarge.copy(
							fontWeight = FontWeight.Bold
						),
					)
				},
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
		}
		
		items(ScanOptions.values()) { option ->
			ScanOptionsItem(
				checked = when (option) {
					ScanOptions.SKIP_TRACKS_SMALLER_THAN_100_KB -> state.isTracksSmallerThan100KBSkipped
					ScanOptions.SKIP_TRACKS_SHORTER_THAN_60_SECONDS -> state.isTracksShorterThan60SecondsSkipped
				},
				option = option,
				onCheckedChange = { checked ->
					viewModel.dispatch(
						when (option) {
							ScanOptions.SKIP_TRACKS_SMALLER_THAN_100_KB -> ScanOptionsAction.SetSkipTracksSmallerThan100KB(
								skip = checked
							)
							ScanOptions.SKIP_TRACKS_SHORTER_THAN_60_SECONDS -> ScanOptionsAction.SetSkipTracksShorterThan60Seconds(
								skip = checked
							)
						}
					)
				},
				modifier = Modifier
					.padding(8.dp)
			)
		}
	}
}
