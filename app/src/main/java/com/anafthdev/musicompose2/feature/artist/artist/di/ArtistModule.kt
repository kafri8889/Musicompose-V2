package com.anafthdev.musicompose2.feature.artist.artist.di

import com.anafthdev.musicompose2.feature.artist.artist.environment.ArtistEnvironment
import com.anafthdev.musicompose2.feature.artist.artist.environment.IArtistEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ArtistModule {
	
	@Binds
	abstract fun provideEnvironment(
		artistEnvironment: ArtistEnvironment
	): IArtistEnvironment
	
}