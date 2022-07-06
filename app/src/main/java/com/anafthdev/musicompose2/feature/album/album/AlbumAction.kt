package com.anafthdev.musicompose2.feature.album.album

sealed interface AlbumAction {
	data class GetAlbum(val id: String): AlbumAction
}