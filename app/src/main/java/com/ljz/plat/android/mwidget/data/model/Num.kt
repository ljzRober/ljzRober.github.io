package com.ljz.plat.android.mwidget.data.model

import com.google.gson.annotations.SerializedName

data class Num(
    @SerializedName("count")
    val count: Int,
    @SerializedName("status_code")
    val statusCode: Int
)
