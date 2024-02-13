package com.codemobiles.android.cmauthenmvvm

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import com.google.firebase.FirebaseApp
import com.pixplicity.easyprefs.library.Prefs

class CMApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()



    }
}