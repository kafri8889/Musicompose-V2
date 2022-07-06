package com.anafthdev.musicompose2.feature.artist.environment

import com.anafthdev.musicompose2.data.model.Album
import com.anafthdev.musicompose2.data.model.Artist
import com.anafthdev.musicompose2.data.model.Song
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

class ArtistEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val repository: Repository
): IArtistEnvironment {
	
	private val _artist = MutableStateFlow(Artist.default)
	private val artist: StateFlow<Artist> = _artist
	
	private val _artistID = MutableStateFlow(Artist.default.id)
	private val artistID: StateFlow<String> = _artistID
	
	init {
		CoroutineScope(dispatcher).launch {
			combine(
				artistID,
				repository.getSongs()
			) { mArtistID, songs ->
				mArtistID to songs
			}.collect { (mArtistID, songs) ->
				val artistList = arrayListOf<Artist>()
				val albumList = arrayListOf<Album>()
				val groupedSongArtist = songs.groupBy { it.artistID }
				val groupedSongAlbum = songs.groupBy { it.albumID }
				
				groupedSongAlbum.forEach { (albumID, songs) ->
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
				
				val groupedAlbum = albumList.groupBy { it.artistID }
				
				groupedSongArtist.forEach { (artistID, songs) ->
					artistList.add(
						Artist(
							id = artistID,
							name = songs[0].artist,
							songs = songs,
							albums = groupedAlbum[artistID] ?: emptyList()
						)
					)
				}
				
				_artist.emit(
					artistList.find { it.id == mArtistID } ?: Artist.default
				)
			}
		}
	}
	
	override fun getArtist(): Flow<Artist> {
		return artist
	}
	
	override suspend fun setArtist(artistID: String) {
		_artistID.emit(artistID)
	}
	
	override suspend fun updateSong(song: Song) {
		repository.updateSongs(song)
	}
	
}