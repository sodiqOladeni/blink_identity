package io.blusalt.simreg.presentation.identity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.blusalt.simreg.data.IdentityRepository
import io.blusalt.simreg.dto.UiData
import io.blusalt.simreg.dto.UserIdentityDto
import javax.inject.Inject

class IdentityCaptureViewModel @Inject constructor(private var identityRepository: IdentityRepository) : ViewModel() {
    var saveIdentityCaptureUiData = MutableLiveData<UiData>()

    fun saveUserIdentity(userIdentityDto: UserIdentityDto){
        identityRepository.saveIdentityToFirestore(userIdentityDto){
            saveIdentityCaptureUiData.value = it
        }
    }
}