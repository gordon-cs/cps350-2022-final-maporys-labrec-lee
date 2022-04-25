package com.example.maporys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.model.LatLng
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

        saveButton.setOnClickListener {
            MainActivity.markerList.add(LatLng(42.588685469711784,
                                                -70.81906923188963))
//            Toast(context).showCustomToast (entryInputMultiLine.text.toString(), context)
            val message = entryInputMultiLine.text.toString()
            var toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
            toast.show()
            val action = NewEntryFragmentDirections.newEntryFragmentToMainFragment()
            findNavController().navigate(action)
        }

    }
}