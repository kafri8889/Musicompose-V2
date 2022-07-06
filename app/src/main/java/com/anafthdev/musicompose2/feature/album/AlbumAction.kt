package com.anafthdev.musicompose2.feature.album

sealed interface AlbumAction {
	data class GetAlbum(val id: String): AlbumAction
}