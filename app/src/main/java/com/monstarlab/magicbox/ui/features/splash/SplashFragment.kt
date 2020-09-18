package com.monstarlab.magicbox.ui.features.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.monstarlab.magicbox.R
import com.monstarlab.magicbox.databinding.FragmentSplashBinding
import com.monstarlab.magicbox.extensions.autoCleared
import com.monstarlab.magicbox.extensions.goneUnless
import com.monstarlab.magicbox.ui.BaseFragment
import kotlinx.coroutines.*

class SplashFragment : BaseFragment() {
    private val activityScope = CoroutineScope(Dispatchers.IO)
    private var binding: FragmentSplashBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val translate = AnimationUtils.loadAnimation(requireContext(), R.anim.translate_top_to_bottom)
        translate.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) { binding.text1.goneUnless(true) }
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                val translate2 = AnimationUtils.loadAnimation(requireActivity(), R.anim.translate_left_to_right)
                translate2.setAnimationListener(object: Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) { binding.text2.goneUnless(true) }
                    override fun onAnimationRepeat(animation: Animation?) {}
                    override fun onAnimationEnd(animation: Animation?) {
                        activityScope.launch {
                            delay(2000)
                            openNewFragment(R.id.action_splash_to_home)
                        }
                    }
                })
                binding.text2.startAnimation(translate2)
            }
        })

        binding.text1.startAnimation(translate)
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }

}