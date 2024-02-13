package com.codemobiles.android.cmmapmvvm.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codemobiles.android.cmmapmvvm.model.NoteRequest
import com.codemobiles.android.cmmapmvvm.util.Constants
import com.codemobiles.android.cmmapmvvm.util.RetrofitClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Polygon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainViewModel(private val app: Application) : ViewModel() {
    private var mLocationProvider: FusedLocationProviderClient? = null
    val mCurrentLocation = MutableStateFlow<Location?>(null)
    val mLastPolygon = MutableStateFlow<Polygon?>(null)
    val mListOfLatLng = MutableStateFlow<ArrayList<LatLng>>(arrayListOf())
    val mLatLngBounds = MutableStateFlow<LatLngBounds?>(null)
    val mToastMessageEvent = SingleLiveEvent<String>()
    val mUpdateLocationTextEvent = SingleLiveEvent<Location>()
    private var mPinCount = 0


    fun getDummyMarker(): BitmapDescriptor {
        return BitmapDescriptorFactory.fromResource(Constants.PIN_DRAWABLES[mPinCount++ % 7])
    }


    fun handleMapLongClick(latLng: LatLng) {
        // clone object before assign new value, otherwise the collector will not be called
        val tmp = mListOfLatLng.value.clone() as ArrayList<LatLng>
        tmp.add(latLng)
        mListOfLatLng.value = tmp

        viewModelScope.launch(Dispatchers.IO) {

//            Method 1#
//            try {
//                val api = RetrofitClient.create();
//                val call = api.postNote(
//                    NoteRequest(
//                        latLng.latitude.toString(),
//                        latLng.longitude.toString(),
//                        "Test"
//                    )
//                )
//                val response = call.execute()
//                withContext(Dispatchers.Main){
//                    mToastMessageEvent.value = response.body()?.result ?: "No Data"
//                }
//
//            }catch (e:Exception){
//
//                withContext(Dispatchers.Main){
//                    mToastMessageEvent.value = e.message ?: "Undefined error"
//                }
//
//            }

            // Method 2#
            val api = RetrofitClient.create()
            val call = api.postPosition(latLng.latitude, latLng.longitude)
            call.enqueue(object: Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    mToastMessageEvent.value = response.body()?.string() ?: "Unknown response"
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    mToastMessageEvent.value = t.message ?: "Unknown error"
                }
            })
        }

    }


    @SuppressLint("MissingPermission")
    fun trackLocation() {

        val request = LocationRequest.create().apply {
            interval = Constants.UPDATE_INTERVAL
            fastestInterval = Constants.FASTEST_INTERVAL
            priority = Priority.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 1000
        }


        if (mLocationProvider == null) {
            mLocationProvider =
                LocationServices.getFusedLocationProviderClient(app.applicationContext)
            mLocationProvider!!.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    if (result.lastLocation != null) {
                        mCurrentLocation.value = result.lastLocation
                        mUpdateLocationTextEvent.value = mCurrentLocation.value
                    }
                }
            }, Looper.myLooper())
        }
    }
}