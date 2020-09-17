package com.monstarlab.magicbox.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.monstarlab.magicbox.MagicBoxApp
import com.monstarlab.magicbox.R
import com.monstarlab.magicbox.data.pref.MagicBoxPreferences
import com.monstarlab.magicbox.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        if (findNavController(R.id.nav_host_fragment).currentDestination?.id!! != R.id.home_fragment) {
            findNavController(R.id.nav_host_fragment).popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MagicBoxPreferences(this).removeAPreferences(Constants.PREF_QUERY)
    }
}