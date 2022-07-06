package com.anafthdev.musicompose2.feature.playlist.playlist_list.di

import com.anafthdev.musicompose2.feature.playlist.playlist_list.environment.IPlaylistListEnvironment
import com.anafthdev.musicompose2.feature.playlist.playlist_list.environment.PlaylistListEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class PlaylistListModule {
	
	@Binds
	abstract fun provideEnvironment(
		playlistListEnvironment: PlaylistListEnvironment
	): IPlaylistListEnvironment
	
}