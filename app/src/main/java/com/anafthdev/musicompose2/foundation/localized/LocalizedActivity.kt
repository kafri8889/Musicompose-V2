package com.anafthdev.musicompose2.foundation.localized

import android.content.Context
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.anafthdev.musicompose2.data.datastore.AppDatastore
import com.anafthdev.musicompose2.data.datastore.AppDatastore.Companion.datastore
import com.anafthdev.musicompose2.data.preference.Language
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import java.util.*

abstract class LocalizedActivity: AppCompatActivity() {

	private val localizedViewModel: LocalizedViewModel by viewModels()
	private var currentLocale: Locale? = null
	
	init {
		lifecycleScope.launchWhenCreated {
			localizedViewModel.effect.collect {
				when (it) {
					is LocalizedEffect.ApplyLanguage -> {
						currentLocale = Locale(it.language.lang)
						LocalizationUtil.applyLanguageContext(
							context = this@LocalizedActivity,
							locale = this@LocalizedActivity.getLocale()
						)
					}
				}
			}
		}
	}
	
	override fun getApplicationContext(): Context {
		val context = super.getApplicationContext()
		return LocalizationUtil.applyLanguageContext(context, context.getLocale())
	}
	
	override fun getBaseContext(): Context {
		val context = super.getBaseContext()
		return LocalizationUtil.applyLanguageContext(context, context.getLocale())
	}
	
	override fun attachBaseContext(newBase: Context) {
		super.attachBaseContext(LocalizationUtil.applyLanguageContext(newBase, newBase.getLocale()))
	}
	
	private fun Context.getLocale(): Locale {
		if (currentLocale == null) {
			runBlocking {
				this@getLocale.datastore.data
					.take(1)
					.map { it[AppDatastore.language] }
					.catch { emit(Language.ENGLISH.ordinal) }
					.collect { languageOrdinal ->
						currentLocale = Locale(
							Language.values()[languageOrdinal ?: 0].lang
						)
					}
			}
		}
		
		return currentLocale!!
	}
	
}