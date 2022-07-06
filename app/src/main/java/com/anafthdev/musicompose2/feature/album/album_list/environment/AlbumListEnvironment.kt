package com.anafthdev.musicompose2.feature.album.album_list.environment

import com.anafthdev.musicompose2.data.SortAlbumOption
import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.data.model.Album
import com.anafthdev.musicompose2.data.repository.Repository
import com.anafthdev.musicompose2.foundation.di.DiName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class AlbumListEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val appDatastore: AppDatastore,
	private val repository: Repository
): IAlbumListEnvironment {
	
	private val _albums = MutableStateFlow(emptyList<Album>())
	private val albums: StateFlow<List<Album>> = _albums
	
	init {
		CoroutineScope(dispatcher).launch {
			combine(
				repository.getSongs(),
				appDatastore.getSortAlbumOption
			) { songs, sortAlbumOption ->
				songs to sortAlbumOption
			}.collect { (songs, sortAlbumOption) ->
				val albumList = arrayListOf<Album>()
				val groupedSong = songs.groupBy { it.albumID }
				
				groupedSong.forEach { (albumID, songs) ->
					albumList.add(
						Album(
							id = albumID,
							name = songs[0].album,
							artist = songs[0].artist,
							artistID = songs[0].artistID,
							songs = songs
						)
					)
				}
				
				_albums.emit(
					when (sortAlbumOption) {
						SortAlbumOption.ALBUM_NAME -> albumList.sortedBy { it.name }
						SortAlbumOption.ARTIST_NAME -> albumList.sortedBy { it.artist }
					}
				)
			}
		}
	}
	
	override fun getAllAlbum(): Flow<List<Album>> {
		return albums
	}
	
}