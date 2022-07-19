package com.edo.githubusernavapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: AppCompatActivity, private val uname: String?) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when (position) {
            0 -> {
                fragment = FollowersFragment()
                val mBundle = Bundle()
                mBundle.putString("UNAME", uname)
                fragment.arguments = mBundle
            }
            1 -> {
                fragment = FollowingFragment()
                val mBundle = Bundle()
                mBundle.putString("UNAME", uname)
                fragment.arguments = mBundle
            }
        }
        return fragment
    }
}