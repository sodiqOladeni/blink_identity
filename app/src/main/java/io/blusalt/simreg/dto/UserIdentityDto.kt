package io.blusalt.simreg.dto

import io.blusalt.simreg.model.UserIdentity

data class UserIdentityDto(
    var firstName: String? = null,
    var lastName: String? = null, var nationality: String? = null,
    var address: String? = null, var personalId: String? = null,
    var documentNumber: String? = null, var sex:String? = null, var dob:String? = null,
    var expiryDate:String? = null
)
fun UserIdentityDto.toUserIdentity(): UserIdentity {
    val userIdentity = UserIdentity()
    userIdentity.firstName = this.firstName
    userIdentity.lastName = this.lastName
    userIdentity.nationality = this.nationality
    userIdentity.address = this.address
    userIdentity.personalId = this.personalId
    userIdentity.documentNumber = this.documentNumber
    userIdentity.sex = this.sex
    userIdentity.dob = this.dob
    userIdentity.expiryDate = this.expiryDate
    return userIdentity
}