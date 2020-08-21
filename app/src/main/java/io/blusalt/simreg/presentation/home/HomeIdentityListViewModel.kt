package io.blusalt.simreg.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.blusalt.simreg.data.IdentityRepository
import io.blusalt.simreg.dto.UiData
import javax.inject.Inject

class HomeIdentityListViewModel @Inject constructor(private var identityRepository: IdentityRepository) : ViewModel() {
    var userIdentitiesUiData = MutableLiveData<UiData>()
    init {
        getUserIdentities()
    }
    private fun getUserIdentities(){
        identityRepository.getUserIdentitiesSavedFromFirestore{
            userIdentitiesUiData.value = it
        }
    }
}