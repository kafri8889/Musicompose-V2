package com.anafthdev.musicompose2.feature.search

sealed interface SearchAction {
	data class Search(val query: String): SearchAction
}