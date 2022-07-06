package com.anafthdev.musicompose2.feature.playlist.di

import com.anafthdev.musicompose2.feature.playlist.environment.IPlaylistEnvironment
import com.anafthdev.musicompose2.feature.playlist.environment.PlaylistEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class PlaylistModule {
	
	@Binds
	abstract fun provideEnvironment(
		playlistEnvironment: PlaylistEnvironment
	): IPlaylistEnvironment
	
}