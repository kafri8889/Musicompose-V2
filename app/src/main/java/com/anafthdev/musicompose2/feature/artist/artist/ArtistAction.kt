package com.anafthdev.musicompose2.feature.artist.artist

sealed interface ArtistAction {
	data class GetArtist(val artistID: String): ArtistAction
}