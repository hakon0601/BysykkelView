package com.example.bysykkelview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bysykkelview.R
import com.example.bysykkelview.models.StationInformation
import com.example.bysykkelview.models.StationStatus
import kotlinx.android.synthetic.main.station_list_row.view.*

class StationListAdapter(): RecyclerView.Adapter<StationListAdapter.MyViewHolder>() {

    private var stationInformationList: List<StationInformation>? = null
    private var stationStatusList: List<StationStatus>? = null

    fun setStationInformationList(stationInformationList: List<StationInformation>?) {
        this.stationInformationList = stationInformationList?.sortedBy{it.name}
    }

    fun setStationStatusList(stationStatusList: List<StationStatus>?) {
        this.stationStatusList = stationStatusList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StationListAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.station_list_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: StationListAdapter.MyViewHolder, position: Int) {
        var stationInformation = stationInformationList?.get(position)
        var stationStatus = stationStatusList?.find { it.station_id == stationInformation?.station_id }
        holder.bind(stationInformation, stationStatus)
    }

    override fun getItemCount(): Int {
        if(stationInformationList == null) return 0
        else return stationInformationList?.size!!
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val tvStationName = view.tvStationName
        val tvNrOfBikes = view.tvNrOfBikes
        val tvNrOfDocks = view.tvNrOfDocks

        fun bind(stationInformation: StationInformation?, stationStatus: StationStatus?) {
            tvStationName.text = stationInformation?.name
            stationStatus?.let {
                tvNrOfBikes.text = it.num_bikes_available.toString()
                tvNrOfDocks.text = it.num_docks_available.toString()
            }
        }


    }

}