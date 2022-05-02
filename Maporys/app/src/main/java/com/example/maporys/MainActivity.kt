package com.example.maporys

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.maporys.data.Entry
import com.example.maporys.data.EntryDatabase
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
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