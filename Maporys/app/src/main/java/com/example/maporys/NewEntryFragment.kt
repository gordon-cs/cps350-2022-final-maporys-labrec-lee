package com.example.maporys

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.maporys.data.Entry
import com.example.maporys.data.EntryViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_new_entry.*


class NewEntryFragment : Fragment(R.layout.fragment_new_entry) {

    private lateinit var  mEntryViewModel: EntryViewModel
    private val args: NewEntryFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newString = args.latitude + ", " + args.longitude
        locationText.text = newString.toEditable()

        mEntryViewModel = ViewModelProvider(this).get(EntryViewModel::class.java)

        cancelButton.setOnClickListener {
            val action = NewEntryFragmentDirections.newEntryFragmentToMainFragment()
            findNavController().navigate(action)
        }

        saveButton.setOnClickListener {
            addEntryToDatabase()
//            Toast(context).showCustomToast (entryInputMultiLine.text.toString(), context)
            val message = entryInputMultiLine.text.toString()
            var toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
            toast.show()
            val action = NewEntryFragmentDirections.newEntryFragmentToMainFragment()
            findNavController().navigate(action)
        }

    }

    private fun addEntryToDatabase() {
        val date = dateText.text.toString()
        val text = entryInputMultiLine.text.toString()
        val locVals = locationText.text.toString().split(", ")
        val lat = locVals[0]
        val long = locVals[1]


        val entry = Entry(0, date, text, lat, long)
        mEntryViewModel.addEntry(entry)

    }

    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}