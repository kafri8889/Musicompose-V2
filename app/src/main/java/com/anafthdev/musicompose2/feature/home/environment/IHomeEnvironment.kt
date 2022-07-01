package com.anafthdev.musicompose2.feature.home.environment

import kotlinx.coroutines.CoroutineDispatcher

interface IHomeEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
}