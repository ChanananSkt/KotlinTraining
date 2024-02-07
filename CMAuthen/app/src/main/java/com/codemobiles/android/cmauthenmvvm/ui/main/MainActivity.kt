package com.codemobiles.android.cmauthenmvvm.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import com.codemobiles.android.cmauthenmvvm.R
import com.codemobiles.android.cmauthenmvvm.databinding.ActivityMainBinding
import com.codemobiles.android.cmauthenmvvm.utils.Constants
import com.pixplicity.easyprefs.library.Prefs

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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


    }

}