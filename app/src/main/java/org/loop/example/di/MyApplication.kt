package org.loop.example.di

import android.app.Application


class MyApplication : Application() {

    companion object {
        @JvmStatic
        lateinit var component : ApplicationComponent
    }



    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder().androidModule(AndroidModule(this)).build()
        component.inject(this)

    }
}