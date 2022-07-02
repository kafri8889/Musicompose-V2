package com.anafthdev.musicompose2.feature.search.environment

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
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class SearchEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val repository: Repository
): ISearchEnvironment {
	
	private val _query = MutableStateFlow("")
	private val query: StateFlow<String> = _query
	
	private val _artists = MutableStateFlow(emptyList<Artist>())
	private val artists: StateFlow<List<Artist>> = _artists
	
	private val _songs = MutableStateFlow(emptyList<Song>())
	private val songs: StateFlow<List<Song>> = _songs
	
	private val _albums = MutableStateFlow(emptyList<Album>())
	private val albums: StateFlow<List<Album>> = _albums
	
	private val songList = arrayListOf<Song>()
	private val albumList = arrayListOf<Album>()
	private val artistList = arrayListOf<Artist>()
	
	init {
		CoroutineScope(dispatcher).launch {
			repository.getSongs().collect { mSongs ->
				val mArtistList = arrayListOf<Artist>()
				val mAlbumList = arrayListOf<Album>()
				val groupedSongArtist = mSongs.groupBy { it.artistID }
				val groupedSongAlbum = mSongs.groupBy { it.albumID }
				
				groupedSongAlbum.forEach { (albumID, songs) ->
					mAlbumList.add(
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
					mArtistList.add(
						Artist(
							id = artistID,
							name = songs[0].artist,
							songs = songs,
							albums = groupedAlbum[artistID] ?: emptyList()
						)
					)
				}
				
				songList.clear()
				albumList.clear()
				artistList.clear()
				
				songList.addAll(mSongs)
				albumList.addAll(mAlbumList)
				artistList.addAll(mArtistList)
			}
		}
		
		CoroutineScope(dispatcher).launch {
			query.collect { q ->
				val filteredArtists = artistList.filter { it.name.contains(q, true) }
				val filteredAlbums = albumList.filter { it.name.contains(q, true) }
				val filteredSongs = songList.filter { it.displayName.contains(q, true) }
				
				_artists.emit(filteredArtists)
				_albums.emit(filteredAlbums)
				_songs.emit(filteredSongs)
			}
		}
	}
	
	override fun getSearchQuery(): Flow<String> {
		return query
	}
	
	override fun getArtists(): Flow<List<Artist>> {
		return artists
	}
	
	override fun getAlbums(): Flow<List<Album>> {
		return albums
	}
	
	override fun getSongs(): Flow<List<Song>> {
		return songs
	}
	
	override suspend fun search(q: String) {
		_query.emit(q)
	}
}