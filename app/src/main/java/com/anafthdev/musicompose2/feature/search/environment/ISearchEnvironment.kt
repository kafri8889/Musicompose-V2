package com.anafthdev.musicompose2.feature.search.environment

import com.anafthdev.musicompose2.data.model.Album
import com.anafthdev.musicompose2.data.model.Artist
import com.anafthdev.musicompose2.data.model.Song
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface ISearchEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun getSearchQuery(): Flow<String>
	
	fun getArtists(): Flow<List<Artist>>
	
	fun getAlbums(): Flow<List<Album>>
	
	fun getSongs(): Flow<List<Song>>
	
	suspend fun search(q: String)
	
}