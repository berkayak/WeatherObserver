package net.berkayak.weatherobserver.viewmodel.dag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Component
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import net.berkayak.weatherobserver.view.ui.MainActivity
import net.berkayak.weatherobserver.viewmodel.InstantWeatherViewModel
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>,
        Provider<ViewModel>>): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModels[modelClass]?.get() as T
    }
}