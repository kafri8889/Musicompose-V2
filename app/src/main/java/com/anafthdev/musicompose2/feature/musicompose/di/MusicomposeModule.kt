package com.anafthdev.musicompose2.feature.musicompose.di

import com.anafthdev.musicompose2.feature.musicompose.environment.IMusicomposeEnvironment
import com.anafthdev.musicompose2.feature.musicompose.environment.MusicomposeEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MusicomposeModule {
	
	@Binds
	abstract fun provideEnvironment(
		environment: MusicomposeEnvironment
	): IMusicomposeEnvironment
	
}