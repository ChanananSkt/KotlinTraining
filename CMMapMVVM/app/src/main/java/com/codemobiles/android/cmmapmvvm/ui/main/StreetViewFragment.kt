package com.codemobiles.android.cmmapmvvm.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.codemobiles.android.cmmapmvvm.R
import com.codemobiles.android.cmmapmvvm.databinding.FragmentStreetviewBinding
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.gms.maps.model.LatLng

class StreetViewFragment : Fragment (R.layout.fragment_streetview){

    private lateinit var binding:FragmentStreetviewBinding
    private val args by navArgs<StreetViewFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStreetviewBinding.inflate(inflater)

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        val streeViewFragment = childFragmentManager.findFragmentById(R.id.streetviewFragment) as SupportStreetViewPanoramaFragment
        streeViewFragment.getStreetViewPanoramaAsync {
            val position = args.position
            it.setPosition(LatLng(position.lat, position.lng))

        }
        return binding.root
    }
}