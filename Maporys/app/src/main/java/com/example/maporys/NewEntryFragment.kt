package com.example.maporys

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.maporys.data.Entry
import com.example.maporys.data.EntryViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_new_entry.*
import java.io.IOException
import java.text.DateFormat
import java.util.*


class NewEntryFragment : Fragment(R.layout.fragment_new_entry) {

    private lateinit var  mEntryViewModel: EntryViewModel
    private val args: NewEntryFragmentArgs by navArgs()
    private var newLatitude: String = ""
    private var newLongitude: String = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newString = args.latitude + ", " + args.longitude
        locationText.text = newString.toEditable()

        val currentDateTimeString: String = DateFormat.getDateTimeInstance().format(Date())
        dateText.text = currentDateTimeString

        mEntryViewModel = ViewModelProvider(this)[EntryViewModel::class.java]

        cancelButton.setOnClickListener {
            val action = NewEntryFragmentDirections.newEntryFragmentToMainFragment()
            findNavController().navigate(action)
        }

        saveButton.setOnClickListener {
            Thread {
                addEntryToDatabase()
            }.start()

            Toast.makeText(requireContext(),
             "Entry Saved!", Toast.LENGTH_SHORT).show()
            val action = NewEntryFragmentDirections.newEntryFragmentToMainFragment()
            findNavController().navigate(action)
        }

        searchButton.setOnClickListener {
            val locationSearch: EditText = locationText
            var location: String
            location = locationSearch.text.toString().trim()
            var addressList: List<Address>? = null

            if (location == null || location == ""){
                Toast.makeText(requireContext(), "Provide Location", Toast.LENGTH_SHORT).show()
            } else {
                val geoCoder = Geocoder(requireContext())
                try {
                    addressList = geoCoder.getFromLocationName(location, 1)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                val address = addressList!![0]
                Toast.makeText(requireContext(),
                    "$location's Latitude and Longitude Inputted", Toast.LENGTH_SHORT).show()
                newLatitude = address.latitude.toString()
                newLongitude = address.longitude.toString()
            }
            val newString = "$newLatitude, $newLongitude"
            locationText.text = newString.toEditable()
        }

    }

    private fun addEntryToDatabase() {
        mEntryViewModel = ViewModelProvider(this)[EntryViewModel::class.java]

        val date = dateText.text.toString()
        val text = entryInputMultiLine.text.toString()
        val locVals = locationText.text.split(", ")
        val lat = locVals[0]
        val long = locVals[1]


        val entry = Entry(0, date, text, lat, long)
        Log.d("newfrag", (entry.date.toString() + entry.text.toString() + entry.lat.toString() + entry.lng.toString()))
        mEntryViewModel.addEntry(entry)
        Log.d("newfrag", "should be inserted")
    }

    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}