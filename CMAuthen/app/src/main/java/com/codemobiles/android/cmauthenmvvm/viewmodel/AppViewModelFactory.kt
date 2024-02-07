package com.codemobiles.android.cmauthenmvvm.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AppViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(app) as T
        }else if (modelClass.isAssignableFrom(FeedViewModel::class.java)) {
            return FeedViewModel(app) as T
        }else if (modelClass.isAssignableFrom(ChartViewModel::class.java)) {
            return ChartViewModel(app) as T
        }else if (modelClass.isAssignableFrom(CameraViewModel::class.java)) {
            return CameraViewModel(app) as T
        }

        throw IllegalArgumentException("UnknownViewModel")
    }
}
