package com.sakb.spl.utils

import java.util.*

object LanguageUtil {
    private lateinit var language: String

    fun getLanguage(): String {
        language = Locale.getDefault().language
        return language
    }

    fun isArabic(): Boolean {
        return getLanguage() == "ar"
    }

    fun isEnglish(): Boolean {
        return getLanguage() == "en"
    }

}