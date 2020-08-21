package io.blusalt.simreg.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.blusalt.simreg.presentation.MainActivity

@Suppress("unused")
@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [FragmentBindingModule::class])
    abstract fun mainActivity(): MainActivity
}