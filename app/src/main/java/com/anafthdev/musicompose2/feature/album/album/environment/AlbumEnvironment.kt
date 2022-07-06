package com.anafthdev.musicompose2.feature.album.album.environment

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

class AlbumEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val repository: Repository
): IAlbumEnvironment {
	
	private val _album = MutableStateFlow(Album.default)
	private val album: StateFlow<Album> = _album
	
	private val _albumID = MutableStateFlow(Album.default.id)
	private val albumID: StateFlow<String> = _albumID
	
	init {
		CoroutineScope(dispatcher).launch {
			combine(
				albumID,
				repository.getSongs()
			) { mAlbumID, songs ->
				mAlbumID to songs
			}.collect { (mAlbumID, songs) ->
				val albumList = arrayListOf<Album>()
				val groupedSong = songs.groupBy { it.albumID }
				
				groupedSong.forEach { (id, songs) ->
					albumList.add(
						Album(
							id = id,
							name = songs[0].album,
							artist = songs[0].artist,
							artistID = songs[0].artistID,
							songs = songs
						)
					)
				}
				
				_album.emit(
					albumList.find { it.id == mAlbumID } ?: Album.default
				)
			}
		}
	}
	
	override fun getAlbum(): Flow<Album> {
		return album
	}
	
	override suspend fun setAlbum(albumID: String) {
		_albumID.emit(albumID)
	}
	
}