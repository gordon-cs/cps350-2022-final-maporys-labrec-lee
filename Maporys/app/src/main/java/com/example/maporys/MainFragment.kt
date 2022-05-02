package com.example.maporys

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.maporys.data.Entry
import com.example.maporys.data.EntryViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

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
//            val action = MainFragmentDirections.mainFragmentToReviewEntry()
//            findNavController().navigate(action)
        }
    }

    override fun onMapReady(googleMap : GoogleMap) {
        displayMarkers(googleMap)
    }


    fun displayMarkers(mMap : GoogleMap) {
        lateinit var entries: List<Entry>
        val entryViewModel = ViewModelProvider(this)[EntryViewModel::class.java]

        Log.d("displayempty", entryViewModel.readAllData.isEmpty().toString())
        if (!entryViewModel.readAllData.isNullOrEmpty()) {
            for (entry in entryViewModel.readAllData) {
                Log.d("display", entry.text.toString())
                val location = LatLng(entry.lat.toDouble(), entry.lng.toDouble())
                mMap.addMarker(MarkerOptions().position(location))
            }
            mMap.setOnMarkerClickListener(this)
        }
    }

    /** Called when the user clicks a marker.
     *  Opens review entry fragment
     *  Provides list of entries at that location*/
    override fun onMarkerClick(marker: Marker): Boolean {
        Log.d("marker", "click")
        val location = marker.position
        val latQuery = location.latitude.toString()
        val lngQuery = location.longitude.toString()

        val entryViewModel = ViewModelProvider(this)[EntryViewModel::class.java]
        val locationEntries = entryViewModel.getEntriesAtLocation(latQuery, lngQuery)
        if (!locationEntries.isNullOrEmpty()) {
            // Call review entry fragment
            MainActivity.currentEntries = locationEntries
        }

        val action = MainFragmentDirections.mainFragmentToReviewEntry()
        findNavController().navigate(action)

        return true;
    }
}