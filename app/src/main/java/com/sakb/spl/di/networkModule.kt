package com.sakb.spl.di

import com.sakb.spl.constants.Constants
import com.sakb.spl.data.local.PrefManager
import com.sakb.spl.data.remote.SplApiEndpoints
import com.sakb.spl.utils.LanguageUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {
    factory { provideInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideSplApi(get()) }
    single { provideRetrofit(get()) }
}

fun provideInterceptor(): Interceptor {
    return HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
}

fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.header("type-dev", Constants.type_dev)
            requestBuilder.header("val-dev", Constants.val_dev)
            requestBuilder.header("lang", LanguageUtil.getLanguage())
            requestBuilder.header("access-token", PrefManager.getUser()?.data?.accessToken ?: "")


            val response = chain.proceed(requestBuilder.build())
            response
        }
        .build()
}

fun provideSplApi(retrofit: Retrofit): SplApiEndpoints = retrofit.create(
    SplApiEndpoints::class.java
)