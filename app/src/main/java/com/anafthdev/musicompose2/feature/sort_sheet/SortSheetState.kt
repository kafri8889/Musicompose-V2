package com.anafthdev.musicompose2.feature.sort_sheet

import com.anafthdev.musicompose2.data.SortAlbumOption
import com.anafthdev.musicompose2.data.SortArtistOption
import com.anafthdev.musicompose2.data.SortSongOption

data class SortSheetState(
	val sortSongOption: SortSongOption = SortSongOption.SONG_NAME,
	val sortAlbumOption: SortAlbumOption = SortAlbumOption.ALBUM_NAME,
	val sortArtistOption: SortArtistOption = SortArtistOption.ARTIST_NAME
)
