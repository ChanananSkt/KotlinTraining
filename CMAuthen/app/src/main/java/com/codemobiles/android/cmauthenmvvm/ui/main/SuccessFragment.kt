package com.codemobiles.android.cmauthenmvvm.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codemobiles.android.cmauthenmvvm.R
import com.codemobiles.android.cmauthenmvvm.databinding.FragmentSuccessBinding
import com.codemobiles.android.cmauthenmvvm.ui.main.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.pixplicity.easyprefs.library.Prefs

class SuccessFragment : Fragment(R.layout.fragment_success) {
    private lateinit var binding:FragmentSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuccessBinding.inflate(inflater)

        binding.logoutBtn.setOnClickListener {
            Prefs.clear()
            findNavController().navigate(SuccessFragmentDirections.actionSuccessFragmentToMainFragment())
        }

        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()

        return binding.root
    }

}