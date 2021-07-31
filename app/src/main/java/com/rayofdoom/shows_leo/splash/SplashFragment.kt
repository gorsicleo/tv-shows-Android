package com.rayofdoom.shows_leo.splash

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rayofdoom.shows_leo.R
import com.rayofdoom.shows_leo.databinding.FragmentSplashBinding



class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val animationTriangle =
            AnimationUtils.loadAnimation(requireContext(), R.anim.animation_triangle)
        val animationTitle = AnimationUtils.loadAnimation(requireContext(), R.anim.animation_title)
        binding.splashTriangle.visibility = View.VISIBLE
        binding.splashTriangle.startAnimation(animationTriangle)
        animationTitle.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val action = SplashFragmentDirections.actionSplashToLogin()
                    findNavController().navigate(action)
                }, 2000)

            }


        })
        animationTriangle.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                binding.showsTitle.visibility = View.VISIBLE
                binding.showsTitle.startAnimation(animationTitle)

            }
        })

    }


    fun animateTitle() {
        with(binding.splashTriangle) {
            translationY = 70f
            animate()
                .translationY(70f)
                .setDuration(1000)
                .setInterpolator(OvershootInterpolator())
                .start()
        }
    }
}