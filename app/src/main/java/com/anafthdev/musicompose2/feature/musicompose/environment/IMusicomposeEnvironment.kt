package com.anafthdev.musicompose2.feature.musicompose.environment

import kotlinx.coroutines.CoroutineDispatcher

interface IMusicomposeEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
}