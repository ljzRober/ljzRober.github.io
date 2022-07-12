package com.ljz.plat.android

import com.google.gson.annotations.SerializedName

data class HttpModel (
    @SerializedName("status_msg")
    val statusMsg: String?,
    @SerializedName("status_code")
    val statusCode: Int?,
    @SerializedName("data")
    val data: ADData?
) {
    data class ADData(
        @SerializedName("adType")
        val adType: String?,
        @SerializedName("adId")
        val adId: Int?,
        @SerializedName("type")
        val type: Int?,
        @SerializedName("url")
        val url: String?,
        @SerializedName("extraData")
        val extraData: MutableList<ExtraData>?
    ) {

    }
    data class ExtraData(
        @SerializedName("extraInt")
        val extraInt: Int?,
        @SerializedName("extraString")
        val extraString: String?
    )
}