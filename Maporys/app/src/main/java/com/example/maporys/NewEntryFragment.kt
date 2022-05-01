package com.example.maporys

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.maporys.data.Entry
import com.example.maporys.data.EntryViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_new_entry.*
import kotlin.concurrent.thread


class NewEntryFragment : Fragment(R.layout.fragment_new_entry) {

    private lateinit var  mEntryViewModel: EntryViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancelButton.setOnClickListener {
            val action = NewEntryFragmentDirections.newEntryFragmentToMainFragment()
            findNavController().navigate(action)
        }

        saveButton.setOnClickListener {
            Thread {
                addEntryToDatabase()
            }.start()

//            Toast(context).showCustomToast (entryInputMultiLine.text.toString(), context)
            val message = entryInputMultiLine.text.toString()
//            var toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
//            toast.show()
            val action = NewEntryFragmentDirections.newEntryFragmentToMainFragment()
            findNavController().navigate(action)
        }

    }

    private fun addEntryToDatabase() {
        mEntryViewModel = ViewModelProvider(this)[EntryViewModel::class.java]

        val date = dateText.text.toString()
        val text = entryInputMultiLine.text.toString()
        val locVals = locationText.text.toString().split(", ")
        val lat = locVals[0]
        val long = locVals[1]


        val entry = Entry(0, date, text, lat, long)
        Log.d("newfrag", (entry.date.toString() + entry.text.toString() + entry.lat.toString() + entry.lng.toString()))
        mEntryViewModel.addEntry(entry)
        Log.d("newfrag", "should be inserted")
    }
}