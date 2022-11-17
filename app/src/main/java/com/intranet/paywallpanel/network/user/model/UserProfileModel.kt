package com.intranet.paywallpanel.network.user.model

import com.google.gson.annotations.SerializedName

data class UserProfileModel(
    @SerializedName("Name")
    var name: String?,
    @SerializedName("Lastname")
    var lastname: String?,
    @SerializedName("Username")
    var username: String?,
    @SerializedName("Email")
    var email: String?,
    @SerializedName("UniqueIdentity")
    var uniqueIdentity: String?,
    @SerializedName("Phone")
    var phone: String?,
    @SerializedName("InsertDateTime")
    var insertDateTime: String?
)