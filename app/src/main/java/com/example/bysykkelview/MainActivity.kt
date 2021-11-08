package com.example.bysykkelview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bysykkelview.models.StationInformation
import com.example.bysykkelview.models.StationStatus

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 10000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Bysykkel Oversikt"
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initViewModel()
    }

    override fun onResume() {
        // Will run every 10 seconds
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            viewModel.makeStationInformationApiCall()
            viewModel.makeStationStatusApiCall()
        }.also { runnable = it }, delay.toLong())
        super.onResume()
    }
    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable!!)
    }


    private fun initViewModel() {

        viewModel.getLiveStationInformationObserver().observe(this, Observer {
            if(it == null) {
                Toast.makeText(this, "Could not get station information", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getLiveStationStatusObserver().observe(this, Observer {
            if(it == null) {
                Toast.makeText(this, "Could not get status information", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getLiveStationInformationAggregationObserver().observe(this, Observer {
            if(it.first?.data?.stations != null && it.second?.data?.stations != null) {
                setContent {
                    StationListComposable(it.first!!.data.stations, it.second!!.data.stations)
                }
            }
        })

        viewModel.makeStationInformationApiCall()
        viewModel.makeStationStatusApiCall()
    }
}

@Composable
private fun StationListComposable(stations: List<StationInformation>, statuses: List<StationStatus>) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
    ) {
        for (station in stations.sortedBy { it.name }) {
            val status = statuses.find { it.station_id == station.station_id }
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(top = 10.dp).fillMaxWidth(),
            ) {

                Text(text = station.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                
                Row(
                    horizontalArrangement = Arrangement.Center,
                ) {
                    status?.let {
                        Image(
                            painterResource(R.drawable.bysykkel_icon),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp,20.dp).padding(end = 5.dp),
                        )

                        Text(text = it.num_bikes_available.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 5.dp))

                        Image(
                            painterResource(R.drawable.bysykkel_dock_icon),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp,20.dp).padding(end = 5.dp)
                        )

                        Text(text = it.num_docks_available.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}