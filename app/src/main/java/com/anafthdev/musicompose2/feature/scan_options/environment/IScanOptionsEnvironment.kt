package com.anafthdev.musicompose2.feature.scan_options.environment

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface IScanOptionsEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun isTracksSmallerThan100KBSkipped(): Flow<Boolean>
	
	fun isTracksShorterThan60SecondsSkipped(): Flow<Boolean>
	
	suspend fun setSkipTracksSmallerThan100KB(skip: Boolean)
	
	suspend fun setSkipTracksShorterThan60Seconds(skip: Boolean)
	
}