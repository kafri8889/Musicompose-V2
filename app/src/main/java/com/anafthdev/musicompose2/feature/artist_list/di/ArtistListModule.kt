package com.anafthdev.musicompose2.feature.artist_list.di

import com.anafthdev.musicompose2.feature.artist_list.environment.ArtistListEnvironment
import com.anafthdev.musicompose2.feature.artist_list.environment.IArtistListEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ArtistListModule {
	
	@Binds
	abstract fun provideEnvironment(
		artistListEnvironment: ArtistListEnvironment
	): IArtistListEnvironment
	
}