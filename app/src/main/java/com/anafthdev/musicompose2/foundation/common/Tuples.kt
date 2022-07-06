/**
 * @author kafri8889
 */
package com.anafthdev.musicompose2.foundation.common

import java.io.Serializable

data class Quad<out A, out B, out C, out D>(
	val first: A,
	val second: B,
	val third: C,
	val fourth: D
) : Serializable {
	
	/**
	 * Returns string representation of the [Quad] including its
	 * [first], [second], [third] and [fourth] values.
	 */
	override fun toString(): String = "($first, $second, $third, $fourth)"
}

data class Quint<out A, out B, out C, out D, out E>(
	val first: A,
	val second: B,
	val third: C,
	val fourth: D,
	val fifth: E
) : Serializable {
	
	/**
	 * Returns string representation of the [Quint] including its
	 * [first], [second], [third], [fourth] and [fifth] values.
	 */
	override fun toString(): String = "($first, $second, $third, $fourth, $fifth)"
}

data class Hexad<out A, out B, out C, out D, out E, out F>(
	val first: A,
	val second: B,
	val third: C,
	val fourth: D,
	val fifth: E,
	val sixth: F
) : Serializable {
	
	/**
	 * Returns string representation of the [Quint] including its
	 * [first], [second], [third], [fourth], [fifth] and [sixth] values.
	 */
	override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth)"
}

data class Heptad<out A, out B, out C, out D, out E, out F, out G>(
	val first: A,
	val second: B,
	val third: C,
	val fourth: D,
	val fifth: E,
	val sixth: F,
	val seventh: G
) : Serializable {
	
	/**
	 * Returns string representation of the [Quint] including its
	 * [first], [second], [third], [fourth], [fifth], [sixth] and [seventh] values.
	 */
	override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh)"
}

/**
 * Converts this quad into a list.
 */
fun <T> Quad<T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth)

/**
 * Converts this quint into a list.
 */
fun <T> Quint<T, T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth, fifth)

/**
 * Converts this hexad into a list.
 */
fun <T> Hexad<T, T, T, T, T, T>.toList(): List<T> = listOf(
	first, second, third, fourth, fifth, sixth
)

/**
 * Converts this heptad into a list.
 */
fun <T> Heptad<T, T, T, T, T, T, T>.toList(): List<T> = listOf(
	first, second, third, fourth, fifth, sixth, seventh
)
