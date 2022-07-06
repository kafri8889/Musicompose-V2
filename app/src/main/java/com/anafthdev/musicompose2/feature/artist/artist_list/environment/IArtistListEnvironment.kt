package com.anafthdev.musicompose2.feature.artist.artist_list.environment

import com.anafthdev.musicompose2.data.model.Artist
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface IArtistListEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun getAllArtist(): Flow<List<Artist>>
	
}