package com.codemobiles.android.cmauthenmvvm.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codemobiles.android.cmauthenmvvm.ui.main.CameraFragment
import com.codemobiles.android.cmauthenmvvm.ui.main.ChartFragment
import com.codemobiles.android.cmauthenmvvm.ui.main.FeedFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val TAB_TITLES = arrayOf("Feed", "Chart", "Camera")
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FeedFragment()
            1 -> ChartFragment()
            2 -> CameraFragment()
            else -> FeedFragment()
        }
    }

    fun getPageTitle(position: Int): CharSequence {
        return TAB_TITLES[position]
    }
}
