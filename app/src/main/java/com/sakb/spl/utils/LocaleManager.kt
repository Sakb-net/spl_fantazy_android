package com.sakb.spl.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.preference.PreferenceManager
import timber.log.Timber
import java.util.*

/*
* to use it
* LocaleManager.setNewLocale(itemView.context, LocaleManager.LANGUAGE_ARABIC)
*  AppUtil.restartApplication(itemView.context)

*
 */
object LocaleManager {
    val LANGUAGE_ENGLISH = "en"
    val LANGUAGE_ARABIC = "ar"
    private const val LANGUAGE_KEY = "LANGUAGE_KEY"

    fun setLocale(c: Context): Context {
        return updateResources(c, getLanguage(c))
    }

    fun setNewLocale(c: Context, language: String): Context {
        persistLanguage(c, language)
        Timber.e("new locale $language")
        return updateResources(c, language)
    }

    fun getLanguage(c: Context): String {
        val prefs = PreferenceManager.getDefaultSharedPreferences(c)
        return prefs.getString(LANGUAGE_KEY, LANGUAGE_ARABIC) ?: LANGUAGE_ARABIC
    }

    @SuppressLint("ApplySharedPref")
    private fun persistLanguage(c: Context, language: String) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(c)
        // use commit() instead of apply(), because sometimes we kill the application process immediately
        // which will prevent apply() to finish
        Timber.e("new language $language")
        prefs.edit().putString(LANGUAGE_KEY, language).commit()
    }

    private fun updateResources(context: Context, language: String): Context {
        var context = context
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources
            .updateConfiguration(config, context.resources.displayMetrics)
        return context
    }

    fun getLocale(res: Resources): Locale {
        val config = res.configuration
        return if (Build.VERSION.SDK_INT >= 24) config.locales.get(0) else config.locale
    }
}