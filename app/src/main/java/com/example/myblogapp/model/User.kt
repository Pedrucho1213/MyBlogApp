package com.example.myblogapp.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @SerializedName("uid"             ) var uid: String?           = null,
    @SerializedName("fullName"        ) var fullName: String?      = null,
    @SerializedName("email"           ) var email: String?         = null,
    )
