package com.anafthdev.musicompose2.feature.playlist_list

import com.anafthdev.musicompose2.data.model.Playlist

data class PlaylistListState(
	val playlists: List<Playlist> = emptyList()
)
