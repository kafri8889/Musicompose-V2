package com.anafthdev.musicompose2.feature.artist.environment

import com.anafthdev.musicompose2.data.model.Artist
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface IArtistEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun getArtist(): Flow<Artist>
	
	suspend fun setArtist(artistID: String)
	
}