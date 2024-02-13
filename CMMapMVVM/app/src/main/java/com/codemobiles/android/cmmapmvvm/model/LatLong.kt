package com.codemobiles.android.cmmapmvvm.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LatLong (val lat:Double, val lng:Double ) : Parcelable