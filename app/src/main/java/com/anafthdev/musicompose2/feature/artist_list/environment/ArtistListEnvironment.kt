package com.anafthdev.musicompose2.feature.artist_list.environment

import com.anafthdev.musicompose2.data.SortArtistOption
import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.data.model.Album
import com.anafthdev.musicompose2.data.model.Artist
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

class ArtistListEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val appDatastore: AppDatastore,
	private val repository: Repository
): IArtistListEnvironment {
	
	private val _artists = MutableStateFlow(emptyList<Artist>())
	private val artists: StateFlow<List<Artist>> = _artists
	
	init {
		CoroutineScope(dispatcher).launch {
			combine(
				repository.getSongs(),
				appDatastore.getSortArtistOption
			) { songs, sortArtistOption ->
				songs to sortArtistOption
			}.collect { (songs, sortArtistOption) ->
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
				
				_artists.emit(
					when (sortArtistOption) {
						SortArtistOption.ARTIST_NAME -> artistList.sortedBy { it.name }
						SortArtistOption.NUMBER_OF_SONGS -> artistList.sortedByDescending { it.songs.size }
					}
				)
			}
		}
	}
	
	override fun getAllArtist(): Flow<List<Artist>> {
		return artists
	}
}