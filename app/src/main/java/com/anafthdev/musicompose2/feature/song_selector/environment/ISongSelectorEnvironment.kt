package com.anafthdev.musicompose2.feature.song_selector.environment

import com.anafthdev.musicompose2.data.SongSelectorType
import com.anafthdev.musicompose2.data.model.Song
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface ISongSelectorEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun getSearchQuery(): Flow<String>
	
	fun getSelectedSong(): Flow<List<Song>>
	
	fun getSongs(): Flow<List<Song>>
	
	suspend fun search(q: String)
	
	suspend fun addSong(song: Song)
	
	suspend fun removeSong(song: Song)
	
	suspend fun getPlaylist(playlistID: Int)
	
	suspend fun setSongSelectorType(type: SongSelectorType)
	
}