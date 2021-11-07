package com.example.bysykkelview.api

import com.example.bysykkelview.models.StationInformationRoot
import com.example.bysykkelview.models.StationStatusRoot
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("station_information.json")
    fun getStationInformation(): Call<StationInformationRoot>

    @GET("station_status.json")
    fun getStationStatus(): Call<StationStatusRoot>
}