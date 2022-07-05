package com.anafthdev.musicompose2.feature.playlist_sheet.environment

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface IPlaylistSheetEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun getPlaylistName(): Flow<String>
	
	suspend fun setPlaylistName(name: String)
	
	suspend fun createPlaylist()
	
}