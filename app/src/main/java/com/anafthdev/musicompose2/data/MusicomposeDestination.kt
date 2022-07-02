package com.anafthdev.musicompose2.data

sealed class MusicomposeDestination(val route: String) {

	object Main: MusicomposeDestination("main")
	
	object Search: MusicomposeDestination("search")

}
