package io.blusalt.simreg.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.blusalt.simreg.presentation.BaseFragment
import io.blusalt.simreg.presentation.identity.IdentityCaptureFragment
import io.blusalt.simreg.presentation.ftue.SignupFragment
import io.blusalt.simreg.presentation.home.HomeIdentityListFragment

@Suppress("unused")
@Module
abstract class FragmentBindingModule {
    @ContributesAndroidInjector
    abstract fun bindBaseFragment(): BaseFragment

    @ContributesAndroidInjector
    abstract fun bindHomeIdentityListFragment(): HomeIdentityListFragment

    @ContributesAndroidInjector
    abstract fun bindSignupFragment(): SignupFragment

    @ContributesAndroidInjector
    abstract fun bindIdentityCaptureFragment(): IdentityCaptureFragment
}