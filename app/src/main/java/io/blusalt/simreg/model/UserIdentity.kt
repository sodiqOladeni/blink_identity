package io.blusalt.simreg.model

import io.blusalt.simreg.dto.UserIdentityDto

class UserIdentity {
    var id: String? = null
    var userId:String? = null
    var firstName: String? = null
    var lastName: String? = null
    var nationality: String? = null
    var address: String? = null
    var personalId: String? = null
    var documentNumber: String? = null
    var sex:String? = null
    var dob:String? = null
    var expiryDate:String? = null
}

fun UserIdentity.toUserIdentityDto(): UserIdentityDto {
    val userIdentityDto = UserIdentityDto()
    userIdentityDto.firstName = this.firstName
    userIdentityDto.lastName = this.lastName
    userIdentityDto.nationality = this.nationality
    userIdentityDto.address = this.address
    userIdentityDto.personalId = this.personalId
    userIdentityDto.documentNumber = this.documentNumber
    userIdentityDto.sex = this.sex
    userIdentityDto.dob = this.dob
    userIdentityDto.expiryDate = this.expiryDate
    return userIdentityDto
}