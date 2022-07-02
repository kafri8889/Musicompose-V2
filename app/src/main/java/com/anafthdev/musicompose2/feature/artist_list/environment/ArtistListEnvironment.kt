package com.anafthdev.musicompose2.feature.artist_list.environment

import com.anafthdev.musicompose2.data.model.Album
import com.anafthdev.musicompose2.data.model.Artist
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

class ArtistListEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val repository: Repository
): IArtistListEnvironment {
	
	private val _artists = MutableStateFlow(emptyList<Artist>())
	private val artists: StateFlow<List<Artist>> = _artists
	
	init {
		CoroutineScope(dispatcher).launch {
			repository.getSongs().collect { songs ->
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
				
				_artists.emit(artistList)
			}
		}
	}
	
	override fun getAllArtist(): Flow<List<Artist>> {
		return artists
	}
}