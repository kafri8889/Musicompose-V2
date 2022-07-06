package com.anafthdev.musicompose2.feature.album.album.di

import com.anafthdev.musicompose2.feature.album.album.environment.AlbumEnvironment
import com.anafthdev.musicompose2.feature.album.album.environment.IAlbumEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AlbumModule {
	
	@Binds
	abstract fun provideEnvironment(
		albumEnvironment: AlbumEnvironment
	): IAlbumEnvironment
	
}