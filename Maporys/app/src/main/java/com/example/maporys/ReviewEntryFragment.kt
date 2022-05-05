package com.example.maporys

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_review_entry.*

class ReviewEntryFragment : Fragment(R.layout.fragment_review_entry) {
    private var entryIndex = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        entryIndex = 0
        val entry = MainActivity.currentEntries[entryIndex]

//        var dateText = findViewById(R.id.dateText) as TextView
        dateText.text = entry.date
        val locationString = (entry.lat + ", " + entry.lng)
        locationText.text = (locationString)
        entryInputMultiLine.text = entry.text

        homeButton.setOnClickListener {
            val action = ReviewEntryFragmentDirections.reviewEntryFragtoMainFrag()
            findNavController().navigate(action)
        }

        olderButton.setOnClickListener {
            showOlderEntry()
        }

    }

    // Updates review entry page with information from next oldest entry at that location
    private fun showOlderEntry() {
        if (entryIndex < (MainActivity.currentEntries.size - 1)) {
            val newEntry = MainActivity.currentEntries[++entryIndex]
            dateText.text = newEntry.date
            val locationString = (newEntry.lat + ", " + newEntry.lng)
            locationText.text = (locationString)
            entryInputMultiLine.text = newEntry.text
        } else {
            Toast.makeText(activity, "No older entries", Toast.LENGTH_SHORT).show()
        }

    }
}