package com.example.bysykkelview.api

import com.example.bysykkelview.models.StationInformationRoot
import com.example.bysykkelview.models.StationStatusRoot
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

const val clientIdentifier: String = "f12f145f-5c9f-4b8e-9a02-7930b5f3f904"

interface ApiService {

    @Headers("Client-Identifier: $clientIdentifier")
    @GET("station_information.json")
    fun getStationInformation(): Call<StationInformationRoot>

    @Headers("Client-Identifier: $clientIdentifier")
    @GET("station_status.json")
    fun getStationStatus(): Call<StationStatusRoot>
}