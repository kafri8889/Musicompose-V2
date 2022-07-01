package com.anafthdev.musicompose2.foundation.localized

import com.anafthdev.musicompose2.data.preference.Language

sealed class LocalizedEffect {
	data class ApplyLanguage(val language: Language) : LocalizedEffect()
}