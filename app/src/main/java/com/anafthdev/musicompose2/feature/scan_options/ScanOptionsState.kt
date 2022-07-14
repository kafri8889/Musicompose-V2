package com.anafthdev.musicompose2.feature.scan_options

data class ScanOptionsState(
	val isTracksSmallerThan100KBSkipped: Boolean = true,
	val isTracksShorterThan60SecondsSkipped: Boolean = true
)
