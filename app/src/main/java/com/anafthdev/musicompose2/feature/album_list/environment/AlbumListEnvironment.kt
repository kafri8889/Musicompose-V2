package com.anafthdev.musicompose2.feature.album_list.environment

import com.anafthdev.musicompose2.data.model.Album
import com.anafthdev.musicompose2.data.repository.Repository
import com.anafthdev.musicompose2.foundation.di.DiName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class AlbumListEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val repository: Repository
): IAlbumListEnvironment {
	
	private val _albums = MutableStateFlow(emptyList<Album>())
	private val albums: StateFlow<List<Album>> = _albums
	
	init {
		CoroutineScope(dispatcher).launch {
			repository.getSongs().collect { songs ->
				val albumList = arrayListOf<Album>()
				val groupedSong = songs.groupBy { it.albumID }
				
				groupedSong.forEach { (albumID, songs) ->
					albumList.add(
						Album(
							id = albumID,
							name = songs[0].album,
							artist = songs[0].artist,
							songs = songs
						)
					)
				}
				
				_albums.emit(albumList)
			}
		}
	}
	
	override fun getAllAlbum(): Flow<List<Album>> {
		return albums
	}
	
}