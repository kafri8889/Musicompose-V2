package com.anafthdev.musicompose2.feature.scan_options

sealed interface ScanOptionsAction {
	data class SetSkipTracksSmallerThan100KB(val skip: Boolean): ScanOptionsAction
	data class SetSkipTracksShorterThan60Seconds(val skip: Boolean): ScanOptionsAction
}