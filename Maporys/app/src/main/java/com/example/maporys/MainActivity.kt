package com.example.maporys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val gordon = LatLng(42.59, -70.82)
        mMap.addMarker(MarkerOptions().position(gordon).title("GoRdOn CoLlEgE"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gordon, 16F))
    }

    companion object Markers {
        var markerList = mutableListOf(LatLng(42.59, -70.82))

    }
}