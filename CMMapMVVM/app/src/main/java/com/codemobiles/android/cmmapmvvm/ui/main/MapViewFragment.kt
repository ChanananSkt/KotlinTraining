package com.codemobiles.android.cmmapmvvm.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.codemobiles.android.cmmapmvvm.R
import com.codemobiles.android.cmmapmvvm.databinding.CustomInfoWindowBinding
import com.codemobiles.android.cmmapmvvm.databinding.FragmentMapviewBinding
import com.codemobiles.android.cmmapmvvm.model.LatLong
import com.codemobiles.android.cmmapmvvm.viewmodel.AppViewModelFactory
import com.codemobiles.android.cmmapmvvm.viewmodel.MainViewModel
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class MapViewFragment : Fragment(R.layout.fragment_mapview) {

    private lateinit var binding: FragmentMapviewBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var mMapView: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapviewBinding.inflate(inflater)
        val factory = AppViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(), factory)[MainViewModel::class.java]

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync {
            mMapView = it
            setupMap()
            checkRuntimePermission()
        }

        observe()
        return binding.root
    }

    private fun observe() {

        val mTrackBtn = binding.mTrackBtn
        val mCurrentLocationTextView = binding.mCurrentLocationTextView
        viewModel.mUpdateLocationTextEvent.observe(requireActivity()) {

            val formatter = DecimalFormat("#,###.000")
            val lat = formatter.format(it.latitude)
            val lng = formatter.format(it.longitude)
            val currentLocStr = String.format("Lat: %s°, Long: %s°", lat, lng)
            mCurrentLocationTextView.text = currentLocStr

            if (mTrackBtn.tag == R.drawable.ic_action_stop) {
                val latLng = LatLng(it.latitude, it.longitude)
                mMapView.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17F))
            }
        }

        viewModel.mToastMessageEvent.observe(requireActivity()) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }



        lifecycleScope.launch {
            viewModel.mListOfLatLng.collect() {

                if (it.size > 0) {
                    val marker = MarkerOptions()
                        .position(it[it.size - 1])
                        .icon(viewModel.getDummyMarker())

                    mMapView.addMarker(marker)
                }


                if (it.size >= 3) {
                    // remove old polygon before create new polygon to avoid overlapping
                    viewModel.mLastPolygon.value?.remove()
                    val option = PolygonOptions()
                    option.addAll(it)
                    option.clickable(true)
                    option.strokeColor(Color.parseColor("#3978DD"))
                    option.fillColor(Color.parseColor("#773978DD"))
                    option.strokeWidth(2f)

                    viewModel.mLastPolygon.value = mMapView.addPolygon(option)
                }

            }
        }
    }


    private fun setupMap() {

        val lat = 13.6954023
        val lng = 100.4864734
        val latLng = LatLng(lat, lng)
        mMapView.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f))

        mMapView.uiSettings.isZoomControlsEnabled = false
        mMapView.isTrafficEnabled = true
        mMapView.mapType = GoogleMap.MAP_TYPE_HYBRID

        mMapView.setOnMapLongClickListener {
            viewModel.handleMapLongClick(it)
        }

        binding.mMapClearBtn.setOnClickListener {
            mMapView.clear()
            viewModel.mListOfLatLng.value.clear()
        }

        binding.mMapSearchBtn.setOnClickListener {
            val startAddress = "18.3189780045837,99.39794591034713"
            val destinationAddress = "18.317647408857454,99.4020881692673"
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=$startAddress&daddr=$destinationAddress")
            )
            startActivity(intent)
        }


        // Set Events Begin
        binding.mTrackBtn.setOnClickListener {
            if (viewModel.mCurrentLocation.value != null) {
                if (binding.mTrackBtn.tag == R.drawable.ic_action_stop) {
                    binding.mTrackBtn.tag = R.drawable.ic_action_start;
                    binding.mTrackBtn.setImageResource(R.drawable.ic_action_start)
                } else {
                    binding.mTrackBtn.tag = R.drawable.ic_action_stop;
                    binding.mTrackBtn.setImageResource(R.drawable.ic_action_stop)
                }
            }
        }

        mMapView.setOnMarkerClickListener {
            Toast.makeText(requireContext(), "${it.position.longitude}", Toast.LENGTH_LONG).show()
            it.showInfoWindow()
            true
        }

        mMapView.setOnInfoWindowClickListener {
            val position = LatLong(it.position.latitude, it.position.longitude)
            findNavController().navigate(MapViewFragmentDirections.actionMapViewFragmentToStreetViewFragment(position))
        }


        mMapView.setInfoWindowAdapter(object: InfoWindowAdapter{
            override fun getInfoContents(p0: Marker): View? {
                return null
            }

            override fun getInfoWindow(marker: Marker): View? {
                val view = CustomInfoWindowBinding.inflate(layoutInflater)
                val latLng = marker.position


                val formatter = DecimalFormat("#,###.000")
                val lat = formatter.format(latLng.latitude) + "°"
                val lng = formatter.format(latLng.longitude) + "°"


                if (marker.title != null && marker.title != "") {
                    view.infoWindowTitle.text = marker.title
                    view.infoWindowTitle.visibility = View.VISIBLE // VISIBLE, INVISIBLE, GONE
                } else {
                    view.infoWindowTitle.visibility = View.GONE
                }

                view.infoWindowPosition.text = "$lat, $lng"
                return view.root
            }
        })

//        demoAddMarker()
    }

    private fun demoAddMarker() {
        val marker = MarkerOptions()
        marker.position(LatLng(13.6954023, 100.4864734))
        mMapView.addMarker(marker)
    }


    @SuppressLint("MissingPermission")
    private fun checkRuntimePermission() {
        // Build the request with the permissions you would like to request and send it.
        permissionsBuilder(Manifest.permission.ACCESS_FINE_LOCATION).build().send {
            if (it.allGranted()) {
                mMapView.isMyLocationEnabled = true
                viewModel.trackLocation()
            } else {
                Toast.makeText(requireContext(), "permission is denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}