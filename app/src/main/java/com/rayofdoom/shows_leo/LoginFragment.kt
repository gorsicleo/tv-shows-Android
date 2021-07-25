package com.rayofdoom.shows_leo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rayofdoom.shows_leo.databinding.FragmentLoginBinding
import com.rayofdoom.shows_leo.utility_functions.*

private const val LOGIN_PASSED_FLAG = "passedLogin"
private const val USERNAME = "username"

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val args: LoginFragmentArgs by navArgs()
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        binding.apply {
            loginButton.isEnabled = false
            if (args.registerSuccess) {
                registerButton.visibility = View.GONE
                loginText.setText(R.string.registration_successful)
            }
            passwordContainer.boxStrokeColor =
                getColor(requireActivity().applicationContext, R.color.white)
            registerButton.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginToRegister()
                findNavController().navigate(action)
            }
        }
        textListenersInit()

        val prefs = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val userRegistered = prefs.getBoolean(LOGIN_PASSED_FLAG, false)
        if (userRegistered) {
            val action = LoginFragmentDirections.actionLoginToShows(
                prefs.getString(
                    USERNAME,
                    USERNAME
                )!!, true
            )
            findNavController().navigate(action)
        }
        viewModel.getLoginResultLiveData().observe(this.viewLifecycleOwner){success->
            if (success) {
                LoginFragmentDirections.actionLoginToShows(
                    binding.emailInput.text.toString(),
                    binding.rememberMeCheckbox.isChecked
                ).also {
                    findNavController().navigate(it)
                }
            } else {
                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun textListenersInit() {

        binding.apply {
            emailInput.addTextChangedValidator { currentText ->
                emailValidator(
                    currentText,
                    { setErrorEmail(getString(R.string.error_email_too_short)) },
                    { setErrorEmail(getString(R.string.error_email_no_at_sign)) },
                    { emailInput.error = null },
                    { enableButton() }
                )
            }
            passwordInput.addTextChangedValidator { currentText ->
                passwordValidator(
                    currentText,
                    { setErrorPass(getString(R.string.error_password_too_short)) },
                    { setErrorPass(getString(R.string.error_password_no_number)) },
                    { passwordInput.error = null },
                    { enableButton() }
                )
            }
        }
    }


    private fun setErrorEmail(errorMessage: String) {
        binding.apply {
            loginButton.isEnabled = false
            emailInput.error = errorMessage
        }
    }


    private fun setErrorPass(errorMessage: String) {
        binding.apply {
            loginButton.isEnabled = false
            passwordInput.error = errorMessage
        }
    }


    private fun enableButton() {
        binding.apply {
            if (emailInput.error == null && passwordInput.error == null && emailInput.text.toString()
                    .isNotEmpty() && passwordInput.text.toString().isNotEmpty()
            ) {
                loginButton.apply {
                    isEnabled = true
                    loginButton.setOnClickListener {
                        viewModel.login(emailInput.text.toString(),passwordInput.text.toString())


                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}