package com.example.loquicleanarchitecture.di.chatlist

import android.app.Application
import com.example.loquicleanarchitecture.di.main.MainScope
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
class ChatlistModule {


    @Provides
    @ChatlistScope
    internal fun providePicasso(context: Application, okHttp3Downloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(context).build()
    }

    @Provides
    @ChatlistScope
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message -> Timber.i(message) }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @ChatlistScope
    fun file(app: Application): File {
        return File(app.applicationContext.getCacheDir(), "okhttp_cache")
    }

    @Provides
    @ChatlistScope
    fun cache(file: File): Cache {
        return Cache(file, (10 * 1000 * 1000).toLong())
    }

    @Provides
    @ChatlistScope
    fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .cache(cache)
            .build()
    }

    @Provides
    @ChatlistScope
    fun okHttp3Downloader(okHttpClient: OkHttpClient): OkHttp3Downloader {
        return OkHttp3Downloader(okHttpClient)
    }
}