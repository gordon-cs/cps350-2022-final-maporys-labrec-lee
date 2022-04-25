package com.example.maporys

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.maporys.data.Entry
import com.example.maporys.data.EntryViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_new_entry.*
import kotlinx.android.synthetic.main.fragment_new_entry.view.*


class NewEntryFragment : Fragment(R.layout.fragment_new_entry) {

    private lateinit var entryViewModel: EntryViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("new", "before")
        entryViewModel = ViewModelProvider(this).get(EntryViewModel::class.java)
        Log.d("new", "made")
        cancelButton.setOnClickListener {
            val action = NewEntryFragmentDirections.newEntryFragmentToMainFragment()
            findNavController().navigate(action)
        }

        saveButton.setOnClickListener {
            // Insert data


            Log.d("new", "after")
            insertEntry()

            // Return to home page
            val action = NewEntryFragmentDirections.newEntryFragmentToMainFragment()
            findNavController().navigate(action)
        }
    }

    private fun insertEntry() {
        val date = dateText.text.toString()

        val text = entryInputMultiLine.text.toString()
        // Update dynamically
        val locationLatLng = LatLng(42.591018040456376,
                                    -70.82371037321192)
        entryViewModel.addEntry(Entry(date, text, locationLatLng));
    }

}