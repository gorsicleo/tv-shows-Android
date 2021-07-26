package com.rayofdoom.shows_leo.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rayofdoom.shows_leo.R
import com.rayofdoom.shows_leo.databinding.FragmentRegisterBinding
import com.rayofdoom.shows_leo.utility_functions.addRepeatPasswordValidator
import com.rayofdoom.shows_leo.utility_functions.addTextChangedValidator
import com.rayofdoom.shows_leo.utility_functions.emailValidator
import com.rayofdoom.shows_leo.utility_functions.passwordValidator

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textListenersInit()
        setLiveDataObserver()
    }

    private fun setLiveDataObserver() {
        viewModel.getRegisterResultLiveData().observe(this.viewLifecycleOwner) { success ->
            if (success) {
                val action =
                    RegisterFragmentDirections.actionRegisterToLogin()
                action.registerSuccess = true
                findNavController().navigate(action)
            } else {
                Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()
            }
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

            //These two repeatPasswordValidators are here to ensure that if even one password field changes, error will inform user about not matching passwords
            passwordRepeatInput.addRepeatPasswordValidator(
                other = passwordInput,
                setErrorNotMatchingPasswords = { setErrorNotMatchingPassword(getString(R.string.passwords_do_not_match)) },
                clearError = { passwordRepeatInput.error = null },
                buttonEnabler = { enableButton() })

            passwordInput.addRepeatPasswordValidator(
                other = passwordRepeatInput,
                setErrorNotMatchingPasswords = { setErrorNotMatchingPassword(getString(R.string.passwords_do_not_match)) },
                clearError = { passwordRepeatInput.error = null },
                buttonEnabler = { enableButton() })
        }
    }


    private fun setErrorNotMatchingPassword(errorMessage: String) {
        binding.apply {
            registerButton.isEnabled = false
            passwordRepeatInput.error = errorMessage
        }
    }


    private fun setErrorEmail(errorMessage: String) {
        binding.apply {
            registerButton.isEnabled = false
            emailInput.error = errorMessage
        }
    }


    private fun setErrorPass(errorMessage: String) {
        binding.apply {
            registerButton.isEnabled = false
            passwordInput.error = errorMessage
        }
    }

    private fun enableButton() {
        binding.apply {
            if (emailInput.error == null && passwordInput.error == null && passwordRepeatInput.error == null && emailInput.text.toString()
                    .isNotEmpty() && passwordInput.text.toString()
                    .isNotEmpty() && passwordRepeatInput.text.toString().isNotEmpty()
            ) {
                registerButton.apply {
                    isEnabled = true
                    registerButton.setOnClickListener {
                        viewModel.register(
                            emailInput.text.toString(),
                            passwordInput.text.toString(),
                            passwordRepeatInput.text.toString()
                        )

                    }
                }
            }
        }
    }
}