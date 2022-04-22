package com.example.maporys

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class NewEntryFragment : Fragment() {
    private var fragment_btn_1: Button? = null
    private var fragment_btn_2: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_entry, container, false)
        fragment_btn_1 = view.findViewById(R.id.saveButton) as Button
        fragment_btn_2 = view.findViewById(R.id.cancelButton) as Button
        fragment_btn_2!!.setOnClickListener { Navigation.findNavController(view).navigate(R.id.navigateMainFragment) }
        return view;
    }

}