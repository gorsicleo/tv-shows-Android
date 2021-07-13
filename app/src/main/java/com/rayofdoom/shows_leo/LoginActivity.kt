package com.rayofdoom.shows_leo

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rayofdoom.shows_leo.databinding.ActivityLoginBinding
import com.rayofdoom.shows_leo.utility_functions.*

private const val AT_SYMBOL = "@"
private const val EMAIL_MINIMUM_LENGTH = 2
private const val PASSWORD_MINIMUM_LENGTH = 6

class LoginActivity : AppCompatActivity() {
    private var conditionEmail = false
    private var conditionPass = false

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //this is used to disable button when app starts
        binding.loginButton.isEnabled = false
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
            loginButton.setBackgroundColor(getColor(R.color.btn_disabled_background_color))
            emailInput.error = errorMessage
        }
    }


    private fun setErrorPass(errorMessage: String) {
        conditionPass = false
        binding.apply {
            loginButton.isEnabled = false
            loginButton.setBackgroundColor(getColor(R.color.btn_disabled_background_color))
            passwordInput.error = errorMessage
        }
    }


    private fun enableButton() {
        if (conditionEmail && conditionPass) {
            binding.loginButton.apply {
                isEnabled = true
                setBackgroundColor(Color.WHITE)
                setTextColor(getColor(R.color.btn_enabled_text_color))
            }
        }
        binding.loginButton.setOnClickListener {
            intent = Intent(this, WelcomeActivity::class.java)
            intent.putExtra("user", binding.emailInput.text.toString())
            startActivity(intent)

        }
    }
}