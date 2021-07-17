package com.rayofdoom.shows_leo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rayofdoom.shows_leo.databinding.FragmentLoginBinding
import com.rayofdoom.shows_leo.utility_functions.addTextChangedValidator

private const val AT_SYMBOL = "@"
private const val EMAIL_MINIMUM_LENGTH = 2
private const val PASSWORD_MINIMUM_LENGTH = 6

class LoginFragment: Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var conditionEmail = false
    private var conditionPass = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.isEnabled = false
        binding.passwordContainer.boxStrokeColor = getColor(requireActivity().applicationContext,R.color.white)
        textListenersInit()

    }

    private fun textListenersInit() {
        binding.emailInput.addTextChangedValidator { currentText -> validateEmail(currentText) }

        binding.passwordInput.addTextChangedValidator { currentText -> validatePass(currentText) }
    }

    private fun validateEmail(inputString: String) {
        if (!inputString.contains(AT_SYMBOL)) {
            setErrorEmail(getString(R.string.error_email_no_at_sign))
        } else if (inputString.length < EMAIL_MINIMUM_LENGTH) {
            setErrorEmail(getString(R.string.error_email_too_short))
        } else {
            conditionEmail = true
            enableButton()
        }

    }

    private fun validatePass(inputString: String) {
        if (inputString.length < PASSWORD_MINIMUM_LENGTH) {
            setErrorPass(getString(R.string.error_password_too_short))
        } else if (!inputString.matches(Regex(".*\\d.*"))) {
            setErrorPass(getString(R.string.error_password_no_number))
        } else {
            conditionPass = true
            enableButton()
        }
    }

    private fun setErrorEmail(errorMessage: String) {
        conditionEmail = false
        binding.apply {
            loginButton.isEnabled = false
            emailInput.error = errorMessage
        }
    }


    private fun setErrorPass(errorMessage: String) {
        conditionPass = false
        binding.apply {
            loginButton.isEnabled = false
            passwordInput.error = errorMessage
        }
    }


    private fun enableButton() {
        if (conditionEmail && conditionPass) {
            binding.loginButton.apply {
                isEnabled = true
            }
        }
        binding.loginButton.setOnClickListener {
            //TODO Not implemented!
            //val action = FirstFragmentDirections.actionFirstToSecond(9)
            //findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}