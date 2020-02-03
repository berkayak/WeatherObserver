package net.berkayak.weatherobserver.view.ui.di

import android.app.Application

open class CoreApp : Application() {

    companion object {
        lateinit var coreComponent: CoreComponent
    }
    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        coreComponent = DaggerCoreComponent.builder().appModule(AppModule(this)).build()
    }

}