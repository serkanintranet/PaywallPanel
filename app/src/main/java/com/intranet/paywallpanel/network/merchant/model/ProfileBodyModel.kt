package com.intranet.paywallpanel.network.merchant.model

import com.google.gson.annotations.SerializedName

data class ProfileBodyModel(
    @SerializedName("Name")
    var name: String?,
    @SerializedName("Title")
    var title: String?,
    @SerializedName("Address")
    var address: String?,
    @SerializedName("IdentityNumber")
    var identityNumber: String?,
    @SerializedName("IdentityAddress")
    var identityAddress: String?,
    @SerializedName("Email")
    var email: String?,
    @SerializedName("Phone")
    var phone: String?,
    @SerializedName("ManagerName")
    var managerName: String?,
    @SerializedName("ManagerLastname")
    var managerLastname: String?,
    @SerializedName("ManagerPhone")
    var managerPhone: String?,
    @SerializedName("ManagerIdentity")
    var managerIdentity: String?,
    @SerializedName("ManagerEmail")
    var managerEmail: String?,
    @SerializedName("ManagerBirthday")
    var managerBirthday: String?,
    @SerializedName("IsActive")
    var isActive: Boolean?,
    @SerializedName("IsDelete")
    var isDelete: Boolean?
)
