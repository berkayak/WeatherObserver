package net.berkayak.weatherobserver.viewmodel.dag

import dagger.Component
import net.berkayak.weatherobserver.view.ui.MainActivity
import javax.inject.Singleton


@Singleton
@Component(modules = [ViewModelModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}