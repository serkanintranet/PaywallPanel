package com.intranet.paywallpanel.ui.main.drawer.profile

import com.google.gson.annotations.SerializedName
import com.intranet.paywallpanel.network.user.model.AttemptsModel

data class AttemptsStaticModel(
    var status: Int?,
    var model: AttemptsModel?
)
