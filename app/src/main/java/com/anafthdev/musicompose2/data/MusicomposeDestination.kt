package com.anafthdev.musicompose2.data

sealed class MusicomposeDestination(val route: String) {

	object Home: MusicomposeDestination("home")

}
