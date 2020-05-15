package com.sakb.spl.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.sakb.spl.data.model.LoginResponse

object PrefManager {
    private const val SRF_SHARED_NAME = "SRF_SHARED_NAME"
    private const val SRF_KEY_USER = "SRF_KEY_USER"

    private const val SRF_KEY_REMEMBER_FLAG = "SRF_KEY_REMEMBER_FLAG"
    private const val SRF_KEY_langs_arr = "SRF_KEY_langs_arr"


    private const val SRF_KEY_CategoryAndSub = "SRF_KEY_CategoryAndSub"
    private const val FTOKEN = "Token"
    private const val Language = "lang"
    private const val cart_cont = "cart_cont"
    private const val countryId = "countryIdcountryId"

    private const val flagIsServiceRunnig = "flagIsServiceRunnig"
    private const val srf_password = "srf_password"

    private const val srf_fcm_chat_item = "srf_fcm_chat_item"


    var sharedPreferences: SharedPreferences? = null
        private set
    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(SRF_SHARED_NAME, MODE_PRIVATE)
    }


    fun saveLanguage(string: String) = sharedPreferences?.edit()?.putString(Language, string)?.apply()

    fun getLanguage(): String = sharedPreferences?.getString(Language, "ar") ?: "ar"

    fun saveUser(user: LoginResponse?) =
        sharedPreferences?.edit()?.putString(SRF_KEY_USER, Gson().toJson(user))?.apply()

    fun getUser():LoginResponse? = Gson().fromJson(
        sharedPreferences?.getString(SRF_KEY_USER, ""),
        LoginResponse::class.java
    )

   fun clear() {
        sharedPreferences?.edit()?.clear()?.apply()
    }






}