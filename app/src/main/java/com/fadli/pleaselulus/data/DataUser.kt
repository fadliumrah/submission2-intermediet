package com.fadli.pleaselulus.data

import com.google.gson.annotations.SerializedName

data class DataUser (
    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("email")
    var email: String? = null,

    @field:SerializedName("password")
    var password: String? = null,
)