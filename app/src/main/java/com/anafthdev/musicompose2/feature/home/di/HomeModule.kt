package com.anafthdev.musicompose2.feature.home.di

import com.anafthdev.musicompose2.feature.home.environment.HomeEnvironment
import com.anafthdev.musicompose2.feature.home.environment.IHomeEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeModule {
	
	@Binds
	abstract fun provideEnvironment(
		environment: HomeEnvironment
	): IHomeEnvironment
	
}