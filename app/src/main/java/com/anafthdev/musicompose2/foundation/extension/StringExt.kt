package com.anafthdev.musicompose2.foundation.extension

/**
 * get start index and end index
 *
 * @return (startIndex, endIndex)
 * @author kafri8889
 */
fun String.indexOf(s: String, ignoreCase: Boolean = false): Pair<Int, Int> {
	val startIndex = indexOf(string = s, ignoreCase = ignoreCase)
	val endIndex = startIndex + (s.length - 1)
	
	return startIndex to endIndex
}
