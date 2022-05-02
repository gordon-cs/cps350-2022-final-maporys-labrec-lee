package com.example.maporys

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_review_entry.*

class ReviewEntryFragment : Fragment(R.layout.fragment_review_entry) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entry = MainActivity.currentEntries[0]

//        var dateText = findViewById(R.id.dateText) as TextView
        dateText.text = entry.date
        val locationString = (entry.lat + ", " + entry.lng)
        locationText.text = (locationString)
        entryInputMultiLine.text = entry.text

        homeButton.setOnClickListener {
            val action = ReviewEntryFragmentDirections.reviewEntryFragtoMainFrag()
            findNavController().navigate(action)
        }
    }
}