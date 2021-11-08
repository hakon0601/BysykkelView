package com.example.bysykkelview.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class StationStatusRoot(
        @Expose
        @SerializedName("last_updated")
        val lastUpdated: Int? = null,
        val data: StationStatusData
        )

data class StationStatusData(val stations: List<StationStatus>)

data class StationStatus(
        @Expose
        @SerializedName("station_id")
        val stationId: Int? = null,
        val num_bikes_available: Int,
        val num_docks_available: Int
        )

