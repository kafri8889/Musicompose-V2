package com.anafthdev.musicompose2.feature.search.di

import com.anafthdev.musicompose2.feature.search.environment.ISearchEnvironment
import com.anafthdev.musicompose2.feature.search.environment.SearchEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SearchModule {
	
	@Binds
	abstract fun provideEnvironment(
		searchEnvironment: SearchEnvironment
	): ISearchEnvironment
	
}