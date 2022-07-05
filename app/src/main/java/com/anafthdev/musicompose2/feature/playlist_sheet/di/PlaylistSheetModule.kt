package com.anafthdev.musicompose2.feature.playlist_sheet.di

import com.anafthdev.musicompose2.feature.playlist_sheet.environment.IPlaylistSheetEnvironment
import com.anafthdev.musicompose2.feature.playlist_sheet.environment.PlaylistSheetEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class PlaylistSheetModule {

	@Binds
	abstract fun provideEnvironment(
		playlistSheetEnvironment: PlaylistSheetEnvironment
	): IPlaylistSheetEnvironment

}