package com.anafthdev.musicompose2.feature.album.environment

import com.anafthdev.musicompose2.data.model.Album
import com.anafthdev.musicompose2.data.model.Song
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface IAlbumEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun getAlbum(): Flow<Album>
	
	suspend fun setAlbum(albumID: String)
	
	suspend fun updateSong(song: Song)
	
}