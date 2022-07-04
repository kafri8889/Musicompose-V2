package com.anafthdev.musicompose2.foundation.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.anafthdev.musicompose2.R
import com.anafthdev.musicompose2.data.SortAlbumOption

@Composable
fun SortAlbumOption.optionToString(): String {
	return stringResource(
		id = when (this) {
			SortAlbumOption.ALBUM_NAME -> R.string.album_name
			SortAlbumOption.ARTIST_NAME -> R.string.artist_name
		}
	)
}
