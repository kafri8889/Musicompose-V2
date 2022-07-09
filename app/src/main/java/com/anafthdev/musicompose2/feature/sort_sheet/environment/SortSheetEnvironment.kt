package com.anafthdev.musicompose2.feature.sort_sheet.environment

import com.anafthdev.musicompose2.data.SortAlbumOption
import com.anafthdev.musicompose2.data.SortArtistOption
import com.anafthdev.musicompose2.data.SortPlaylistOption
import com.anafthdev.musicompose2.data.SortSongOption
import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.foundation.di.DiName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class SortSheetEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val appDatastore: AppDatastore
): ISortSheetEnvironment {
	
	override fun getSortSongOption(): Flow<SortSongOption> {
		return appDatastore.getSortSongOption
	}
	
	override fun getSortAlbumOption(): Flow<SortAlbumOption> {
		return appDatastore.getSortAlbumOption
	}
	
	override fun getSortArtistOption(): Flow<SortArtistOption> {
		return appDatastore.getSortArtistOption
	}
	
	override fun getSortPlaylistOption(): Flow<SortPlaylistOption> {
		return appDatastore.getSortPlaylistOption
	}
	
	override suspend fun setSortSongOption(option: SortSongOption) {
		appDatastore.setSortSongOption(option)
	}
	
	override suspend fun setSortAlbumOption(option: SortAlbumOption) {
		appDatastore.setSortAlbumOption(option)
	}
	
	override suspend fun setSortArtistOption(option: SortArtistOption) {
		appDatastore.setSortArtistOption(option)
	}
	
	override suspend fun setSortPlaylistOption(option: SortPlaylistOption) {
		appDatastore.setSortPlaylistOption(option)
	}
}