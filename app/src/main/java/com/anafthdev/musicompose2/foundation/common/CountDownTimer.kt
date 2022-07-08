package com.anafthdev.musicompose2.foundation.common

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CountDownTimer(
	private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
	
	private var _isRunning = MutableStateFlow(false)
	val isRunning: StateFlow<Boolean> = _isRunning
	
	private var currentJob: Job? = null
	private var currentTime = 0L
	private var currentTimeUpdated = false
	
	fun run(
		millisInFuture: Long,
		interval: Long,
		cancelIfRunning: Boolean = false,
		onTick: (Long) -> Unit,
		onFinish: () -> Unit
	) {
		if (cancelIfRunning) tryCancel()
		if (!isRunning.value or cancelIfRunning) {
			currentJob = CoroutineScope(dispatcher).launch {
				_isRunning.emit(true)
				
				while (currentTime < interval) {
					currentTime += millisInFuture
					
					if (!currentTimeUpdated) delay(millisInFuture)
					
					currentTimeUpdated = false
					onTick(currentTime)
				}
				
				onFinish()
			}
		}
	}
	
	fun updateTime(time: Long) {
		currentTime = time
		currentTimeUpdated = true
	}
	
	fun tryCancel(): Boolean {
		return if (currentJob != null) {
			currentJob!!.cancel("canceled manually!")
			true
		} else false
	}

}
