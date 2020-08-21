package io.blusalt.simreg.di

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import io.blusalt.simreg.data.IdentityRepository
import io.blusalt.simreg.data.SharedPref
import io.blusalt.simreg.data.UserRepository
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class DataModule {

    @Singleton
    @Provides
    fun providePref(app:Application): SharedPref = SharedPref(app)

    @Singleton
    @Provides
    fun providesFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun providesIdentityRepository(firestore: FirebaseFirestore, auth: FirebaseAuth):
            IdentityRepository = IdentityRepository(firestore, auth)

    @Singleton
    @Provides
    fun providesUserRepository(auth: FirebaseAuth):
            UserRepository = UserRepository(auth)
}