package com.anafthdev.musicompose2.feature.scan_options.di

import com.anafthdev.musicompose2.feature.scan_options.environment.IScanOptionsEnvironment
import com.anafthdev.musicompose2.feature.scan_options.environment.ScanOptionsEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ScanOptionsModule {
	
	@Binds
	abstract fun provideEnvironment(
		scanOptionsEnvironment: ScanOptionsEnvironment
	): IScanOptionsEnvironment
	
}