package com.anafthdev.musicompose2.foundation.localized

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import java.util.*

object LocalizationUtil {
	
	fun applyLanguageContext(context: Context, locale: Locale?): Context {
		if (locale == null) return context
		if (locale == getLocale(context.resources.configuration)) return context
		
		return try {
			setupLocale(locale)
			val resources = context.resources
			val configuration = getOverridingConfig(locale, resources)
			
			resources.updateConfiguration(configuration, resources.displayMetrics)
			context.createConfigurationContext(configuration)
		} catch (exception: Exception) {
			context
		}
	}
	
	private fun setupLocale(locale: Locale) {
		Locale.setDefault(locale)
		
		LocaleList.setDefault(LocaleList(locale))
	}
	
	private fun getOverridingConfig(locale: Locale, resources: Resources): Configuration {
		val configuration = resources.configuration
		
		configuration.setLocales(LocaleList(locale))
		
		return configuration
	}
	
	private fun getLocale(configuration: Configuration): Locale {
		return configuration.locales.get(0)
	}
	
}
