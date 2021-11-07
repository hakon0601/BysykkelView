package com.example.bysykkelview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bysykkelview.adapter.StationListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    lateinit var recyclerAdapter: StationListAdapter
    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 10000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Bysykkel Oversikt"
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initRecyclerView()
        initViewModel()
    }

    override fun onResume() {
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            viewModel.makeStationInformationApiCall()
            viewModel.makeStationStatusApiCall()
//            Toast.makeText(this@MainActivity, "This method will run every 10 seconds", Toast.LENGTH_SHORT).show()
        }.also { runnable = it }, delay.toLong())
        super.onResume()
    }
    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable!!)
    }

    private fun initRecyclerView() {
        stationListRecyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = StationListAdapter()
        stationListRecyclerView.adapter = recyclerAdapter
    }

    private fun initViewModel() {

        viewModel.getLiveStationInformationObserver().observe(this, Observer {
            if(it != null) {
                recyclerAdapter.setStationInformationList(it.data.stations)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Could not get station information", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getLiveStationStatusObserver().observe(this, Observer {
            if(it != null) {
                recyclerAdapter.setStationStatusList(it.data.stations)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Could not get station status", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.makeStationInformationApiCall()
        viewModel.makeStationStatusApiCall()
    }

    override fun onDestroy() {
        super.onDestroy()
        //viewModel.cancelJobs()
    }
}