package com.anafthdev.musicompose2.feature.home.environment

import com.anafthdev.musicompose2.data.model.Song
import kotlinx.coroutines.CoroutineDispatcher

interface IHomeEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	suspend fun updateSong(song: Song)
	
}