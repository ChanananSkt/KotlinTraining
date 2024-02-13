package com.codemobiles.android.cmauthenmvvm.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.codemobiles.android.cmauthenmvvm.R
import com.codemobiles.android.cmauthenmvvm.databinding.ActivityMainBinding
import com.codemobiles.android.cmauthenmvvm.utils.Constants
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.pixplicity.easyprefs.library.Prefs

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setupNotification()
        }

        // Set entry fragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val navInflater = navController.navInflater
        val navGraph = navInflater.inflate(R.navigation.nav_graph)

        val isAuthenPassed = Prefs.getBoolean(Constants.AUTHEN_PASSED)
        if (isAuthenPassed) {
            navGraph.setStartDestination(R.id.successFragment)
        } else {
            navGraph.setStartDestination(R.id.mainFragment)
        }


        navController.graph = navGraph

        // Get token
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isComplete) {
                val token = task.result;
                Log.i(Constants.APP_TAG, "token: $token")
                //  binding.pushToken.text = token
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupNotification() {
        // Sets up permissions request launcher.
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                Log.i("Push Permission:", "Allow")
            }else{
                Log.i("Push Permission:", "Not Allow")
            }
        }

        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)) {
            Log.i("Push Permission:", "Allow")
            FirebaseMessaging.getInstance().subscribeToTopic("demo")
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Subscription successful
                        println("Subscribed to topic successfully")
                    } else {
                        // Subscription failed
                        println("Subscription failed")
                    }
                }
        }
        else {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }


        FirebaseMessaging.getInstance().token.addOnSuccessListener { result ->
            if(result != null){
                Log.i("Push token:", result);
            }
        }
    }

}