package org.loop.example.di

import dagger.Component
import org.loop.example.DataLoader
import org.loop.example.view.MainActivity
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AndroidModule::class))
interface ApplicationComponent {
    fun inject(application: MyApplication)

    fun inject(mainActivity: MainActivity)

    fun inject(dataLoader: DataLoader)
}
