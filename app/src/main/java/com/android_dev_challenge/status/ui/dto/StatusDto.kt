package com.android_dev_challenge.status.ui.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class StatusDto(
    val parentStatus: String,
    val childStatus: List<ChildStatus>
) {

    @Parcelize
    data class ChildStatus(
        val parentStatusName: String,
        val childStatusName: String,
        val url: String?,
        val responseCode: Int,
        val responseTime: Double,
        val statusClass: String
    ) : Parcelable
}
