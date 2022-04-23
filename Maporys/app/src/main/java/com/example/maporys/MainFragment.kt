package com.example.maporys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment(R.layout.fragment_main) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newEntryButton.setOnClickListener {
            val action = MainFragmentDirections.mainFragmentToNewEntryFragment()
            findNavController().navigate(action)
        }
        reviewEntryButton.setOnClickListener {
            val action = MainFragmentDirections.mainFragmentToReviewEntry()
            findNavController().navigate(action)
        }

    }
}