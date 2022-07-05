package com.anafthdev.musicompose2.foundation.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.SortPlaylistOption

@Composable
fun SortPlaylistOption.optionToString(): String {
	return stringResource(
		id = when (this) {
			SortPlaylistOption.PLAYLIST_NAME -> R.string.playlist_name
			SortPlaylistOption.NUMBER_OF_SONGS -> R.string.number_of_songs
		}
	)
}
