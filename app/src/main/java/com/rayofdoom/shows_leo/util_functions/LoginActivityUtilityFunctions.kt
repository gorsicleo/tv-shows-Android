package com.rayofdoom.shows_leo.util_functions

import android.content.Intent
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import com.rayofdoom.shows_leo.*


fun LoginActivity.textListenersInit() {
    binding.emailInput.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val inputString = binding.emailInput.text
            validateEmail(inputString.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    })

    binding.passwordInput.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val inputString = binding.passwordInput.text
            validatePass(inputString.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    })
}


fun LoginActivity.validateEmail(inputString: String) {
    if (!inputString.contains("@")) {
        setErrorEmail(getString(R.string.error_email_no_at_sign))
    } else if (inputString.length < 2) {
        setErrorEmail(getString(R.string.error_email_too_short))
    } else {
        conditionEmail = true
        enableButton()
    }

}


fun LoginActivity.validatePass(inputString: String) {
    if (inputString.length < 6) {
        setErrorPass(getString(R.string.error_password_too_short))
    } else if (!inputString.matches(Regex(".*\\d.*"))) {
        setErrorPass(getString(R.string.error_password_no_number))
    } else {
        conditionPass = true
        enableButton()
    }
}


fun LoginActivity.setErrorEmail(errorMessage: String) {
    conditionEmail = false
    binding.apply {
        loginButton.isEnabled = false
        loginButton.setBackgroundColor(getColor(R.color.btn_disabled_background_color))
        emailInput.error = errorMessage
    }
}


fun LoginActivity.setErrorPass(errorMessage: String) {
    conditionPass = false
    binding.apply {
        loginButton.isEnabled = false
        loginButton.setBackgroundColor(getColor(R.color.btn_disabled_background_color))
        passwordInput.error = errorMessage
    }
}


fun LoginActivity.enableButton() {
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


