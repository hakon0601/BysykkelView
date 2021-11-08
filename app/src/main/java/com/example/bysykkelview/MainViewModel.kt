package com.example.bysykkelview

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bysykkelview.api.RetrofitBuilder
import com.example.bysykkelview.models.StationInformationRoot
import com.example.bysykkelview.models.StationStatusRoot
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    var liveStationInformationRoot: MutableLiveData<StationInformationRoot> = MutableLiveData()
    var liveStationStatusRoot: MutableLiveData<StationStatusRoot> = MutableLiveData()

    val liveStationInformationAggregation: MediatorLiveData<Pair<StationInformationRoot?, StationStatusRoot?>> =
            MediatorLiveData<Pair<StationInformationRoot?, StationStatusRoot?>>().apply {
                addSource(liveStationInformationRoot) { value = Pair(it, liveStationStatusRoot.value) }
                addSource(liveStationStatusRoot) { value = Pair(liveStationInformationRoot.value, it) }
    }

    fun getLiveStationInformationObserver(): MutableLiveData<StationInformationRoot> {
        return liveStationInformationRoot
    }

    fun getLiveStationStatusObserver(): MutableLiveData<StationStatusRoot> {
        return liveStationStatusRoot
    }

    fun getLiveStationInformationAggregationObserver(): MediatorLiveData<Pair<StationInformationRoot?, StationStatusRoot?>> {
        return liveStationInformationAggregation
    }

    fun makeStationInformationApiCall() {
        val stationInformationCall  = RetrofitBuilder.apiService.getStationInformation()
        stationInformationCall.enqueue(object : Callback<StationInformationRoot> {
            override fun onFailure(call: Call<StationInformationRoot>, t: Throwable) {
                liveStationInformationRoot.postValue(null)
            }

            override fun onResponse(
                call: Call<StationInformationRoot>,
                response: Response<StationInformationRoot>
            ) {
                liveStationInformationRoot.postValue(response.body())
            }
        })
    }

    fun makeStationStatusApiCall() {
        val stationStatusCall  = RetrofitBuilder.apiService.getStationStatus()
        stationStatusCall.enqueue(object : Callback<StationStatusRoot> {
            override fun onFailure(call: Call<StationStatusRoot>, t: Throwable) {
                liveStationStatusRoot.postValue(null)
            }

            override fun onResponse(
                call: Call<StationStatusRoot>,
                response: Response<StationStatusRoot>
            ) {
                liveStationStatusRoot.postValue(response.body())
            }
        })
    }
}