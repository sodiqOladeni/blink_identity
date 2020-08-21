package io.blusalt.simreg.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.blusalt.simreg.presentation.ftue.SignupViewModel
import io.blusalt.simreg.presentation.identity.IdentityCaptureViewModel
import io.blusalt.simreg.presentation.home.HomeIdentityListViewModel

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(IdentityCaptureViewModel::class)
    abstract fun bindIdentityCaptureViewModel(identityCaptureViewModel: IdentityCaptureViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeIdentityListViewModel::class)
    abstract fun bindHomeIdentityListViewModel(homeIdentityListViewModel: HomeIdentityListViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignupViewModel::class)
    abstract fun bindSignupViewModel(signupViewModel: SignupViewModel):ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory):ViewModelProvider.Factory
}