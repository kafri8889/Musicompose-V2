package com.anafthdev.musicompose2.feature.sort_sheet

import com.anafthdev.musicompose2.data.SortAlbumOption
import com.anafthdev.musicompose2.data.SortArtistOption
import com.anafthdev.musicompose2.data.SortPlaylistOption
import com.anafthdev.musicompose2.data.SortSongOption

sealed interface SortSheetAction {
	data class SetSortSongOption(val option: SortSongOption): SortSheetAction
	data class SetSortAlbumOption(val option: SortAlbumOption): SortSheetAction
	data class SetSortArtistOption(val option: SortArtistOption): SortSheetAction
	data class SetSortPlaylistOption(val option: SortPlaylistOption): SortSheetAction
}