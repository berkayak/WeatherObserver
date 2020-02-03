package net.berkayak.weatherobserver.view.ui.di

import dagger.Component
import dagger.Module
import dagger.Provides
import net.berkayak.weatherobserver.view.ui.MainActivity


@WDScope
@Component(dependencies = [CoreComponent::class], modules = [WDModule::class])
interface WDComponent {
    fun inject(activity: MainActivity)
}

@Module
class WDModule {

    @Provides
    @WDScope
    fun viewModelFactory(): WDViewModelFactory = WDViewModelFactory()

}