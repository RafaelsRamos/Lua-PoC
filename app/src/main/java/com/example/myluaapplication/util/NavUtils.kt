package com.example.myluaapplication.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.myluaapplication.R

fun FragmentActivity.addFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .add(R.id.frameLayout, fragment)
        .addToBackStack(null)
        .commit()
}

fun FragmentActivity.backFragment() {
    supportFragmentManager.popBackStack()
}