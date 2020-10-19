package com.android_dev_challenge.status.remote_repository.webservice.entity

import com.android_dev_challenge.status.common.AppConstants
import com.fasterxml.jackson.annotation.*

// TODO: data model to be changed to get apisDBs and sites as dynamic properties
data class StatusApiResponse(
    @JsonProperty(AppConstants.APIS_DBS)
    val apisDBs: LinkedHashMap<String, Status>,
    @JsonProperty(AppConstants.SITES)
    val sites: LinkedHashMap<String, Status>,
){
    data class Status(
        @JsonProperty("url")
        val url: String?,
        @JsonProperty("responseCode")
        val responseCode: Int,
        @JsonProperty("responseTime")
        val responseTime: Double,
        @JsonProperty("class")
        val statusClass: String
    ){
        override fun toString(): String {
            return "Status: \n" +
                    "Url: $url \n" +
                    "responseCode: $responseCode \n" +
                    "responseTime: $responseTime \n" +
                    "statusClass: $statusClass\n"
        }
    }
}