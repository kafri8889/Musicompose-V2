package com.anafthdev.musicompose2.feature.album.album.environment

import com.anafthdev.musicompose2.data.model.Album
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface IAlbumEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun getAlbum(): Flow<Album>
	
	suspend fun setAlbum(albumID: String)
	
}