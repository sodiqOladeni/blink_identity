package io.blusalt.simreg.data

import com.google.firebase.auth.FirebaseAuth
import io.blusalt.simreg.dto.UiData

class UserRepository(private val auth: FirebaseAuth) {

    companion object{
        const val TAG = "UserRepository"
        const val USER_CREATED_SUCCESSFULLY = "User created successfully"
        const val ERROR_CREATING_USER = "Error creating user"
        const val USER_LOGIN_SUCCESSFULLY = "User login successfully"
        const val ERROR_LOGIN = "Error login"
    }

    fun createUserWithEmail(email:String, password:String, uiData:(UiData) -> Unit){
        val emailAuth = auth.createUserWithEmailAndPassword(email, password)
        emailAuth.addOnCompleteListener {
            if (emailAuth.isSuccessful){
                uiData(UiData(emailAuth.isSuccessful, USER_CREATED_SUCCESSFULLY))
            }else{
                uiData(UiData(emailAuth.isSuccessful, "$ERROR_CREATING_USER ${emailAuth.exception?.localizedMessage}"))
            }
        }
    }

    fun loginUserWithEmail(email:String, password:String, uiData:(UiData) -> Unit){
        val emailAuth = auth.signInWithEmailAndPassword(email, password)
        emailAuth.addOnCompleteListener {
            if (emailAuth.isSuccessful){
                uiData(UiData(emailAuth.isSuccessful, USER_LOGIN_SUCCESSFULLY))
            }else{
                uiData(UiData(emailAuth.isSuccessful, "$ERROR_LOGIN ${emailAuth.exception?.localizedMessage}"))
            }
        }
    }
}