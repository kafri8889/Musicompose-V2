package com.anafthdev.musicompose2.feature.scan_options.environment

import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.foundation.di.DiName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class ScanOptionsEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	private val appDatastore: AppDatastore
): IScanOptionsEnvironment {
	
	private val _isTracksSmallerThan100KBSkipped = MutableStateFlow(true)
	private val isTracksSmallerThan100KBSkipped: StateFlow<Boolean> = _isTracksSmallerThan100KBSkipped
	
	private val _isTracksShorterThan60SecondsSkipped = MutableStateFlow(true)
	private val isTracksShorterThan60SecondsSkipped: StateFlow<Boolean> = _isTracksShorterThan60SecondsSkipped
	
	init {
		CoroutineScope(dispatcher).launch {
			combine(
				appDatastore.isTracksSmallerThan100KBSkipped,
				appDatastore.isTracksShorterThan60SecondsSkipped
			) { kbSkipped, secSkipped ->
				kbSkipped to secSkipped
			}.collect { (kbSkipped, secSkipped) ->
				_isTracksSmallerThan100KBSkipped.emit(kbSkipped)
				_isTracksShorterThan60SecondsSkipped.emit(secSkipped)
			}
		}
	}
	
	override fun isTracksSmallerThan100KBSkipped(): Flow<Boolean> {
		return isTracksSmallerThan100KBSkipped
	}
	
	override fun isTracksShorterThan60SecondsSkipped(): Flow<Boolean> {
		return isTracksShorterThan60SecondsSkipped
	}
	
	override suspend fun setSkipTracksSmallerThan100KB(skip: Boolean) {
		appDatastore.setSkipTracksSmallerThan100KB(skip)
	}
	
	override suspend fun setSkipTracksShorterThan60Seconds(skip: Boolean) {
		appDatastore.setSkipTracksShorterThan60Seconds(skip)
	}
	
}