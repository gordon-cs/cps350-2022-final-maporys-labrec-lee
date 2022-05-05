package com.example.maporys

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.maporys.data.Entry
import com.example.maporys.data.EntryViewModel
import com.example.maporys.data.EntryDatabase
import com.example.maporys.databinding.FragmentMainBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApi
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_new_entry.*
import java.io.IOException

class MainFragment : Fragment(R.layout.fragment_main), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener,
    ActivityCompat.OnRequestPermissionsResultCallback{

    private var permissionDenied = false
    private var db: EntryDatabase? = null
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private var latitude = "0.0"
    private var longitude = "0.0"
    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private var mGoogleApiClient: GoogleApiClient? = null
    private lateinit var mLocationRequest: com.google.android.gms.location.LocationRequest
    private var mCurrLocationMarker: Marker? = null


    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val mapFragment = SupportMapFragment.newInstance()
        childFragmentManager
            .beginTransaction()
            // This adds the map fragment to the container we choose.
            .replace(R.id.map, mapFragment)
            .commitNow()

        childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newEntryButton.setOnClickListener {

            val action = MainFragmentDirections.mainFragmentToNewEntryFragment(latitude, longitude)
            findNavController().navigate(action)
        }
//        reviewEntryButton.setOnClickListener {
////            val action = MainFragmentDirections.mainFragmentToReviewEntry()
////            findNavController().navigate(action)
//        }
    }

    override fun onMapReady(googleMap : GoogleMap) {
        mMap = googleMap
        enableMyLocation()
        mMap.uiSettings.isZoomControlsEnabled = true
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
            mMap.setOnMapLongClickListener(this)
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

        mMap.uiSettings.isZoomControlsEnabled = true
        enableMyLocation()
        return true;
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     * Code from location data maps sdk example.
     */
    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            mMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->
                if (location != null) {
                    lastLocation = location
                    val currentLatLong = LatLng(location.latitude, location.longitude)

                    latitude = location.latitude.toString()
                    longitude = location.longitude.toString()

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12F))
                }
            }
            return
        }

        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION),
            MainFragment.LOCATION_PERMISSION_REQUEST_CODE
        )

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults)
            return
        }

        if (isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION) || isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_COARSE_LOCATION))
        {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation()
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true
        }
    }

    private fun isPermissionGranted(grantPermissions: Array<String>,
                                    grantResults: IntArray,
                                    permission: String): Boolean {
        for (i in grantPermissions.indices) {
            if (permission == grantPermissions[i]) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED
            }
        }
        return false
    }

    companion object Markers {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onMapLongClick(location: LatLng) {
        val action = MainFragmentDirections.mainFragmentToNewEntryFragment(location.latitude.toString(),
                                                                           location.longitude.toString())
        findNavController().navigate(action)
    }

}