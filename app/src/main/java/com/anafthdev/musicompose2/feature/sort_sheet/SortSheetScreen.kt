package com.anafthdev.musicompose2.feature.sort_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.SortAlbumOption
import com.anafthdev.musicompose2.data.SortArtistOption
import com.anafthdev.musicompose2.data.SortSongOption
import com.anafthdev.musicompose2.data.SortType
import com.anafthdev.musicompose2.foundation.extension.optionToString
import com.anafthdev.musicompose2.foundation.uicomponent.SortItem

@Composable
fun SortSheetScreen(
	sortType: SortType,
	navController: NavController
) {
	
	val viewModel = hiltViewModel<SortSheetViewModel>()
	
	val state by viewModel.state.collectAsState()
	
	val mSortType by rememberUpdatedState(newValue = sortType)
	
	Column(
		modifier = Modifier
			.padding(bottom = 24.dp)
	) {
		Text(
			text = stringResource(id = R.string.sort_by),
			style = MaterialTheme.typography.titleMedium.copy(
				fontWeight = FontWeight.Bold
			),
			modifier = Modifier
				.align(Alignment.CenterHorizontally)
				.padding(vertical = 16.dp)
		)
		
		when (mSortType) {
			SortType.SONG -> {
				SortSongOption.values().forEach { option ->
					SortItem(
						text = option.optionToString(),
						selected = state.sortSongOption == option,
						onClick = {
							navController.popBackStack()
							viewModel.dispatch(
								SortSheetAction.SetSortSongOption(option)
							)
						}
					)
				}
			}
			SortType.ALBUM -> {
				SortAlbumOption.values().forEach { option ->
					SortItem(
						text = option.optionToString(),
						selected = state.sortAlbumOption == option,
						onClick = {
							navController.popBackStack()
							viewModel.dispatch(
								SortSheetAction.SetSortAlbumOption(option)
							)
						}
					)
				}
			}
			SortType.ARTIST -> {
				SortArtistOption.values().forEach { option ->
					SortItem(
						text = option.optionToString(),
						selected = state.sortArtistOption == option,
						onClick = {
							navController.popBackStack()
							viewModel.dispatch(
								SortSheetAction.SetSortArtistOption(option)
							)
						}
					)
				}
			}
		}
	}
}
