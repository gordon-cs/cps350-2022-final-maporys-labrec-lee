package com.example.maporys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class MainFragment : Fragment() {
    private var fragment_btn_1: Button? = null
    private var fragment_btn_2: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        fragment_btn_1 = view.findViewById(R.id.reviewEntryButton) as Button
        fragment_btn_2 = view.findViewById(R.id.newEntryButton) as Button
        fragment_btn_2!!.setOnClickListener { Navigation.findNavController(view).navigate(R.id.navigateToNewEntry) }
        return view;
    }
}