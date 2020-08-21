package io.blusalt.simreg.presentation

import android.app.Activity
import android.app.Application
import com.microblink.MicroblinkSDK
import com.microblink.intent.IntentDataTransferMode
import io.blusalt.simreg.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MainApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        MicroblinkSDK.setLicenseFile("io.blusalt.simreg.blinkid_Android_2020_09_19.mblic", this)
        // use optimised way for transferring RecognizerBundle between activities, while ensuring
        // data does not get lost when Android restarts the scanning activity
        MicroblinkSDK.setIntentDataTransferMode(IntentDataTransferMode.PERSISTED_OPTIMISED)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}