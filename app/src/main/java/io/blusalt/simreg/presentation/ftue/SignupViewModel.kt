package io.blusalt.simreg.presentation.ftue

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.blusalt.simreg.data.UserRepository
import io.blusalt.simreg.dto.UiData
import javax.inject.Inject

class SignupViewModel @Inject constructor(private var userRepository: UserRepository):ViewModel() {
    val userSignupResultUiData = MutableLiveData<UiData>()
    val loginUserResultUiData = MutableLiveData<UiData>()

    fun createUser(email:String, password:String){
        userRepository.createUserWithEmail(email, password) {
            userSignupResultUiData.value = it
        }
    }

    fun loginUser(email:String, password:String){
        userRepository.loginUserWithEmail(email, password) {
            loginUserResultUiData.value = it
        }
    }
}