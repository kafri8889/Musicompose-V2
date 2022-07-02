package com.anafthdev.musicompose2.feature.artist_list

import com.anafthdev.musicompose2.data.model.Artist

data class ArtistListState(
	val artists: List<Artist> = emptyList()
)
