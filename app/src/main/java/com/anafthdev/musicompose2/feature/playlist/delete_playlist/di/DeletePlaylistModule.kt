package com.anafthdev.musicompose2.feature.playlist.delete_playlist.di

import com.anafthdev.musicompose2.feature.playlist.delete_playlist.environment.DeletePlaylistEnvironment
import com.anafthdev.musicompose2.feature.playlist.delete_playlist.environment.IDeletePlaylistEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DeletePlaylistModule {
	
	@Binds
	abstract fun provideEnvironment(
		deletePlaylistEnvironment: DeletePlaylistEnvironment
	): IDeletePlaylistEnvironment
	
}