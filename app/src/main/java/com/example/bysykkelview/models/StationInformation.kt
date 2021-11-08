package com.example.bysykkelview.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StationInformationRoot(
        @Expose
        @SerializedName("last_updated")
        val lastUpdated: Int? = null,
        val data: StationInformationData)

data class StationInformationData(val stations: List<StationInformation>)

data class StationInformation(
        @Expose
        @SerializedName("station_id")
        val stationId: Int? = null,
        val name: String
)