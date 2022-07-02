package com.anafthdev.musicompose2.feature.musicompose.environment

import com.anafthdev.musicompose2.data.model.Song
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface IMusicomposeEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun getSongs(): Flow<List<Song>>
	
	fun getCurrentPlayedSong(): Flow<Song>
	
	fun isPlaying(): Flow<Boolean>
	
	suspend fun play(song: Song)
	
	suspend fun pause()
	
	suspend fun resume()
	
}