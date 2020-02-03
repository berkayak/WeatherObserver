package net.berkayak.weatherobserver.view.ui.di

import javax.inject.Singleton

@Singleton
object ComponentBuilds {

    private var wdComponent: WDComponent? = null

    fun wdComponent(): WDComponent {
        if (wdComponent == null)
            wdComponent =
                DaggerWDComponent.builder().coreComponent(CoreApp.coreComponent).build()
        return wdComponent as WDComponent
    }

}