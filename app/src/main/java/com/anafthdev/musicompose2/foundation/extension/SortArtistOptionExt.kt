package com.anafthdev.musicompose2.foundation.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.SortArtistOption

@Composable
fun SortArtistOption.optionToString(): String {
	return stringResource(
		id = when (this) {
			SortArtistOption.ARTIST_NAME -> R.string.artist_name
			SortArtistOption.NUMBER_OF_SONGS -> R.string.number_of_songs
		}
	)
}
