package com.anafthdev.musicompose2.feature.sort_sheet.environment

import com.anafthdev.musicompose2.data.SortAlbumOption
import com.anafthdev.musicompose2.data.SortArtistOption
import com.anafthdev.musicompose2.data.SortSongOption
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface ISortSheetEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun getSortSongOption(): Flow<SortSongOption>
	
	fun getSortAlbumOption(): Flow<SortAlbumOption>
	
	fun getSortArtistOption(): Flow<SortArtistOption>
	
	suspend fun setSortSongOption(option: SortSongOption)
	
	suspend fun setSortAlbumOption(option: SortAlbumOption)
	
	suspend fun setSortArtistOption(option: SortArtistOption)
	
}