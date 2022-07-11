package com.anafthdev.musicompose2.foundation.extension

/**
 * Move element.
 * @author kafri8889
 */
fun <T> Collection<T>.move(from: Int, to: Int): List<T> {
	if (from == to) return this.toList()
	return ArrayList(this).apply {
		val temp = get(from)
		removeAt(from)
		add(to, temp)
	}
}
