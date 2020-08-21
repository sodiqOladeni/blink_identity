package io.blusalt.simreg.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.blusalt.simreg.dto.UiData
import io.blusalt.simreg.dto.UserIdentityDto
import io.blusalt.simreg.dto.toUserIdentity
import io.blusalt.simreg.model.UserIdentity
import io.blusalt.simreg.model.toUserIdentityDto

class IdentityRepository(private var firestore: FirebaseFirestore, private var auth: FirebaseAuth) {

    companion object{
        const val TAG = "IdentityRepository"
        // These can be replaced with remote messages.
        const val USER_IDENTITY_PATH = "User_Identities"
        const val IDENTITY_SAVED = "Identity saved successfully"
        const val ERROR_SAVING_IDENTITY = "Error saving identity"
        const val ERROR_FETCHING_IDENTITY = "Error fetching identity"
        const val IDENTITY_FETCHED = "Identity fetched"
        const val USER_ID = "userId"
    }

    fun saveIdentityToFirestore(userIdentityDto: UserIdentityDto, uiDataContent:(UiData) -> Unit){
        val userIdentity = userIdentityDto.toUserIdentity()
        userIdentity.userId = auth.currentUser?.uid
        val saveData = firestore.collection(USER_IDENTITY_PATH).add(userIdentity)
        saveData.addOnCompleteListener {
            if (saveData.isSuccessful){
                uiDataContent(UiData(saveData.isSuccessful, IDENTITY_SAVED))
            }else{
                uiDataContent(UiData(saveData.isSuccessful, ERROR_SAVING_IDENTITY))
            }
        }
    }

    fun getUserIdentitiesSavedFromFirestore(uiDataContent:(UiData) -> Unit){
        val storedIdentity = firestore.collection(USER_IDENTITY_PATH)
            .whereEqualTo(USER_ID, auth.currentUser?.uid).get()
        storedIdentity.addOnCompleteListener {
            if (storedIdentity.isSuccessful){
                val identities = storedIdentity.result?.documents
                val userIdentityDto = identities?.map {
                    it.toObject(UserIdentity::class.java)?.toUserIdentityDto()
                }
                uiDataContent(UiData(storedIdentity.isSuccessful, IDENTITY_FETCHED, userIdentityDto))
            }else{
                uiDataContent(UiData(storedIdentity.isSuccessful, ERROR_FETCHING_IDENTITY))
            }
        }
    }
}