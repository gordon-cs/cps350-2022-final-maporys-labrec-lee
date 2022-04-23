package com.example.maporys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_new_entry.*
import kotlinx.android.synthetic.main.fragment_new_entry.view.*

class NewEntryFragment : Fragment(R.layout.fragment_new_entry) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cancelButton.setOnClickListener {
            val action = NewEntryFragmentDirections.newEntryFragmentToMainFragment()
            findNavController().navigate(action)
        }
    }
}