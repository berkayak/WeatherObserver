package net.berkayak.weatherobserver.viewmodel.dag

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import net.berkayak.weatherobserver.viewmodel.InstantWeatherViewModel

@Module
abstract class ViewModelModule(val app: Application) {

    @Binds
    @IntoMap
    @ViewModelKey(InstantWeatherViewModel::class)
    internal abstract fun bindViewModel(viewModel: InstantWeatherViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}