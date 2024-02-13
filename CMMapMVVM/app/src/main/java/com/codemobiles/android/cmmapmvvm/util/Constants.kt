package com.codemobiles.android.cmmapmvvm.util

import com.codemobiles.android.cmmapmvvm.R

class Constants {
    companion object {

        const val BASE_URL = "http://10.0.2.2:3000/"
        const val LAT = "LAT"
        const val LNG = "LNG"

        val PIN_DRAWABLES = arrayOf(
            R.drawable.pin_01,
            R.drawable.pin_02,
            R.drawable.pin_03,
            R.drawable.pin_04,
            R.drawable.pin_05,
            R.drawable.pin_06,
            R.drawable.pin_07
        )

        // rate in milliseconds at which your app prefers to receive location updates. Note that the location updates may be somewhat faster or slower than this rate to optimize for battery usage, or there may be no updates at all (if the device has no connectivity, for example).
        const val UPDATE_INTERVAL: Long = 1000

        // fastest rate in milliseconds at which your app can handle location updates. Unless your app benefits from receiving updates more quickly than the rate specified in setInterval(), you don't need to call this method.
        val FASTEST_INTERVAL: Long = 300

    }
}