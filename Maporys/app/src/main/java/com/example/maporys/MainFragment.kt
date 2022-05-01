package com.example.maporys

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.example.maporys.data.Entry
import com.example.maporys.data.EntryViewModel
import com.example.maporys.databinding.FragmentMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main), OnMapReadyCallback {

//    private lateinit var mMap: GoogleMap
//    private var mapReady = false

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newEntryButton.setOnClickListener {
            val action = MainFragmentDirections.mainFragmentToNewEntryFragment()
            findNavController().navigate(action)
        }
        reviewEntryButton.setOnClickListener {
            val action = MainFragmentDirections.mainFragmentToReviewEntry()
            findNavController().navigate(action)
        }
    }

    override fun onMapReady(googleMap : GoogleMap) {
        displayMarkers(googleMap)

        // Add a marker in Sydney and move the camera
//        val gordon = LatLng(42.59, -70.82)
//        val gordon = LatLng(42.5, -70.8)
//        mMap.addMarker(MarkerOptions().position(gordon).title("GoRdOn CoLlEgE"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gordon, 16F))
//
//        for (location in MainActivity.markerList) {
//            mMap.addMarker(MarkerOptions().position(location).title("Jenks"))
//        }
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gordon, 16F))
    }


    fun displayMarkers(mMap : GoogleMap) {
        lateinit var entries: List<Entry>
//        Thread {
//            entries = getData()
//            return@Thread
//        }.start().

        val entryViewModel = ViewModelProvider(this).get(EntryViewModel::class.java)

        Log.d("displayempty", entryViewModel.readAllData.isEmpty().toString())
        if (!entryViewModel.readAllData.isNullOrEmpty()) {
            for (entry in entryViewModel.readAllData) {
                Log.d("display", entry.text.toString())
                val location = LatLng(entry.lat.toDouble(), entry.lng.toDouble())
//                var toast = Toast.makeText(context, entry.lng, Toast.LENGTH_LONG)
//                toast.show()
                mMap.addMarker(MarkerOptions().position(location))
            }
        }
    }

//    private fun getData() : List<Entry> {
//        val entryViewModel = ViewModelProvider(this).get(EntryViewModel::class.java)
//        return entryViewModel.readAllData
//    }
}