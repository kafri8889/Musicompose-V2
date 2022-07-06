package com.anafthdev.musicompose2.feature.playlist.playlist_list.environment

import com.anafthdev.musicompose2.data.SortPlaylistOption
import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.data.model.Playlist
import com.anafthdev.musicompose2.data.repository.Repository
import com.anafthdev.musicompose2.foundation.di.DiName
import com.anafthdev.musicompose2.utils.AppUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class PlaylistListEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val appDatastore: AppDatastore,
	private val repository: Repository
): IPlaylistListEnvironment {
	
	private val _playlists = MutableStateFlow(emptyList<Playlist>())
	private val playlist: StateFlow<List<Playlist>> = _playlists
	
	init {
		CoroutineScope(dispatcher).launch {
			combine(
				repository.getPlaylists(),
				appDatastore.getSortPlaylistOption
			) { mPlaylists, sortPlaylistOption ->
				mPlaylists to sortPlaylistOption
			}.collect { (mPlaylists, sortPlaylistOption) ->
				val defaultPlaylists = mPlaylists.filter { it.isDefault }
				val nonDefaultPlaylists = mPlaylists.filterNot { it.isDefault }
				
				val sortedPlaylist = when (sortPlaylistOption) {
					SortPlaylistOption.PLAYLIST_NAME -> nonDefaultPlaylists.sortedWith(
						Comparator { o1, o2 ->
							return@Comparator AppUtil.collator.compare(o1.name, o2.name)
						}
					)
					SortPlaylistOption.NUMBER_OF_SONGS -> nonDefaultPlaylists.sortedByDescending { it.songs.size }
				}
				
				_playlists.emit(defaultPlaylists + sortedPlaylist)
			}
		}
	}
	
	override fun getPlaylists(): Flow<List<Playlist>> {
		return playlist
	}
	
	override suspend fun newPlaylist(playlist: Playlist) {
		repository.insertPlaylists(playlist)
	}
	
	
}