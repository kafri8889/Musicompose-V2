package com.anafthdev.musicompose2.feature.album_list.environment

import com.anafthdev.musicompose2.data.model.Album
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface IAlbumListEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun getAllAlbum(): Flow<List<Album>>
	
}