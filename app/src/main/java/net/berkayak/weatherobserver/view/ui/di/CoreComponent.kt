package net.berkayak.weatherobserver.view.ui.di

import android.content.Context
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
    interface CoreComponent {
    fun context(): Context
}
