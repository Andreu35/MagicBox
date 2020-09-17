package com.monstarlab.magicbox.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater

abstract class BaseFragment : Fragment() {
    protected lateinit var transitionName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireActivity()).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(requireActivity()).inflateTransition(android.R.transition.move)
        exitTransition = TransitionInflater.from(requireActivity()).inflateTransition(android.R.transition.move)
    }

    fun openNewFragmentWithTransition(bundle: Bundle?, view: View, action: Int) {
        val extras = FragmentNavigator.Extras.Builder().addSharedElement(view, view.transitionName).build()
        findNavController().navigate(action, bundle, null, extras)
    }

    fun openNewFragment(action: Int) {
        findNavController().navigate(action, null, null)
    }

    fun setUpToolbar(toolbar: Toolbar, showTitle: Boolean, showHome: Boolean) {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayShowTitleEnabled(showTitle)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(showHome)
    }

    fun onBackPressed(){
        (activity as AppCompatActivity?)!!.onBackPressed()
    }
}