package com.anafthdev.musicompose2.feature.album.album_list.di

import com.anafthdev.musicompose2.feature.album.album_list.environment.AlbumListEnvironment
import com.anafthdev.musicompose2.feature.album.album_list.environment.IAlbumListEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AlbumListModule {
	
	@Binds
	abstract fun provideEnvironment(
		albumListEnvironment: AlbumListEnvironment
	): IAlbumListEnvironment
	
}