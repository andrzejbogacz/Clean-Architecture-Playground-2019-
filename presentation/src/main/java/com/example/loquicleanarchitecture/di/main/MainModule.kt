package com.example.loquicleanarchitecture.di.main

import android.app.Application
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.File

@Module
class MainModule {

    @MainScope
    @Provides
    internal fun providePicasso(context: Application, okHttp3Downloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(context).build()
    }

    @Provides
    @MainScope
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message -> Timber.i(message) }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @MainScope
    fun file(app: Application): File {
        return File(app.applicationContext.getCacheDir(), "okhttp_cache")
    }

    @Provides
    @MainScope
    fun cache(file: File): Cache {
        return Cache(file, (10 * 1000 * 1000).toLong())
    }

    @Provides
    @MainScope
    fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .cache(cache)
            .build()
    }

    @Provides
    @MainScope
    fun okHttp3Downloader(okHttpClient: OkHttpClient): OkHttp3Downloader {
        return OkHttp3Downloader(okHttpClient)
    }

}