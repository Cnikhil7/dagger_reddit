package org.loop.example.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import org.loop.example.RedditApiInterface
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * A module for Android-specific dependencies which require a [android.content.Context] or [ ] to create.
 */
@Module
class AndroidModule(private val application: Application) {

    /**
     * Allow the application context to be injected but require that it be annotated with [ ][ForApplication] to explicitly differentiate it from an activity context.
     */
    @Provides
    @Singleton
    @ForApplication
    fun provideApplicationContext(): Context = application


    @Provides
    @Singleton
    fun provideClient(): OkHttpClient = OkHttpClient().newBuilder().build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.reddit.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        return retrofit
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): RedditApiInterface {
        return retrofit.create(RedditApiInterface::class.java)
    }

}
