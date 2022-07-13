package com.anafthdev.musicompose2.feature.music_player_sheet.environment

import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.data.model.Song
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface IMusicPlayerSheetEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun getPlaylists(): Flow<List<Playlist>>
	
	suspend fun addToPlaylist(song: Song, playlist: Playlist)
	
}