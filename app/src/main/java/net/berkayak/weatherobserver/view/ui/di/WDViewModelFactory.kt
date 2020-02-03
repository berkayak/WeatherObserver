package net.berkayak.weatherobserver.view.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.berkayak.weatherobserver.viewmodel.InstantWeatherViewModel

@Suppress("UNCHECKED_CAST")
class WDViewModelFactory:
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InstantWeatherViewModel() as T
    }
}
