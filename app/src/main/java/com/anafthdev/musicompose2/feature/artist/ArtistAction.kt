package com.anafthdev.musicompose2.feature.artist

import com.anafthdev.musicompose2.data.model.Song

sealed interface ArtistAction {
	data class GetArtist(val artistID: String): ArtistAction
	data class UpdateSong(val song: Song): ArtistAction
}