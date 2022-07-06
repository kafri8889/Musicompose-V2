package com.anafthdev.musicompose2.feature.album

import com.anafthdev.musicompose2.data.model.Song

sealed interface AlbumAction {
	data class GetAlbum(val id: String): AlbumAction
	data class UpdateSong(val song: Song): AlbumAction
}