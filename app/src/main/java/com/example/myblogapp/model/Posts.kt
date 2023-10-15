package com.example.myblogapp.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Posts(
    @SerializedName("id"                ) var id: String? = null,
    @SerializedName("title"             ) var title: String?           = null,
    @SerializedName("authorName"        ) var authorName: String?           = null,
    @SerializedName("authorId"          ) var authorId: String?           = null,
    @SerializedName("content"           ) var content: String?           = null,
    @SerializedName("date"              ) var date: String?           = null,
    )
