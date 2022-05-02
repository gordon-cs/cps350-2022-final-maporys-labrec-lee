package com.example.maporys

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.maporys.data.Entry
import com.example.maporys.data.EntryDatabase
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.example.maporys.data.EntryViewModel
import com.google.android.gms.maps.model.MarkerOptions


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private var db: EntryDatabase? = null
    var entryViewModel : List<Entry> = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = EntryDatabase.getDatabase(context = this)

        val mapFragment = SupportMapFragment.newInstance()
        //Special transactions for fragments
        supportFragmentManager
            .beginTransaction()
            // This adds the map fragment to the container we choose.
            .replace(R.id.map, mapFragment)
            .commitNow()

        supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        displayMarkers(googleMap)
    }

    fun displayMarkers(mMap : GoogleMap) {
        val entryViewModel = ViewModelProvider(this)[EntryViewModel::class.java]
        if (!entryViewModel.readAllData.isNullOrEmpty()) {
            for (entry in entryViewModel.readAllData) {
                Log.d("display", entry.text)
                val location = LatLng(entry.lat.toDouble(), entry.lng.toDouble())
                mMap.addMarker(MarkerOptions().position(location))
            }
        }
    }

    companion object {
        lateinit var currentEntries : List<Entry>
    }
}