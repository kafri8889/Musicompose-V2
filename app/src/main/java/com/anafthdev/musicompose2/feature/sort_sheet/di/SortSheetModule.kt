package com.anafthdev.musicompose2.feature.sort_sheet.di

import com.anafthdev.musicompose2.feature.sort_sheet.environment.ISortSheetEnvironment
import com.anafthdev.musicompose2.feature.sort_sheet.environment.SortSheetEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SortSheetModule {
	
	@Binds
	abstract fun provideEnvironment(
		sortSheetEnvironment: SortSheetEnvironment
	): ISortSheetEnvironment
	
}