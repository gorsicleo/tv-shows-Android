package com.rayofdoom.shows_leo.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rayofdoom.shows_leo.R
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
        if (args.registerSuccess) {
            loginAfterSuccessfulRegistration()
        }

        binding.registerButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginToRegister()
            findNavController().navigate(action)
        }


        textListenersInit()

        setLiveDataObserver()

    }


    private fun setLiveDataObserver() {
        viewModel.getLoginResultLiveData().observe(this.viewLifecycleOwner) { success ->
            if (success) {
                PrefsUtil.putHeadersToPrefs(viewModel.getHeaders(), requireActivity())
                PrefsUtil.putUserToPrefs(viewModel.getUser(), requireActivity())
                LoginFragmentDirections.actionLoginToShows(
                    binding.emailInput.text.toString(),
                    binding.rememberMeCheckbox.isChecked
                ).also {
                    findNavController().navigate(it)
                }
            } else {
                Toast.makeText(context, getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginAfterSuccessfulRegistration() {
        binding.apply {
            registerButton.visibility = View.GONE
            loginText.setText(R.string.registration_successful)
        }
    }


    private fun textListenersInit() {

        binding.apply {
            emailInput.addTextChangedValidator { currentText ->
                emailValidator(
                    inputString = currentText,
                    setErrorShortEmail = { setErrorEmail(getString(R.string.error_email_too_short)) },
                    setErrorInvalidFormat = { setErrorEmail(getString(R.string.error_email_no_at_sign)) },
                    clearError = { emailInput.error = null },
                    buttonEnabler = { enableButton() }
                )
            }
            passwordInput.addTextChangedValidator { currentText ->
                passwordValidator(
                    inputString = currentText,
                    setErrorShortPassword = { setErrorPass(getString(R.string.error_password_too_short)) },
                    setErrorInvalidFormat = { setErrorPass(getString(R.string.error_password_no_number)) },
                    clearError = { passwordInput.error = null },
                    buttonEnabler = { enableButton() }
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
                        viewModel.login(emailInput.text.toString(), passwordInput.text.toString())


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