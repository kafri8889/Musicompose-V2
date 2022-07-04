package com.anafthdev.musicompose2.data

sealed class MusicomposeDestination(open val route: String) {

	object Main: MusicomposeDestination("main")
	
	object Search: MusicomposeDestination("search")
	
	object Setting: MusicomposeDestination("setting")
	
	object Language: MusicomposeDestination("language")
	
	object Theme: MusicomposeDestination("theme")
	
	class BottomSheet {
		object Sort: MusicomposeDestination("bottom-sheet/sort/{type}") {
			fun createRoute(type: SortType): String {
				return "bottom-sheet/sort/${type.ordinal}"
			}
		}
	}

}
