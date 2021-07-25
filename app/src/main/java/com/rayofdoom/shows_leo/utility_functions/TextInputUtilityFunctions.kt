package com.rayofdoom.shows_leo.utility_functions

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

private const val AT_SYMBOL = "@"
private const val EMAIL_MINIMUM_LENGTH = 2
private const val PASSWORD_MINIMUM_LENGTH = 6
private const val CONTAINS_NUMBER_PATTERN = ".*\\d.*"

/**
 * Function that encapsulates adding textChangedListener to validate user
 * input
 */
fun TextInputEditText.addTextChangedValidator(validate: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            validate(s.toString())

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    })
}

/**
 * Checks if inputString satisfies minimum e-mail requirements
 * regarding length and format in order stop user from proceeding.
 * If those requirements are not satisfied error functions are being called,
 * otherwise functions clearError and buttonEnabler are being called
 */
fun emailValidator(
    inputString: String,
    setErrorShortEmail: () -> Unit,
    setErrorInvalidFormat: () -> Unit,
    clearError: () -> Unit,
    buttonEnabler: () -> Unit
) {
    if (!inputString.contains(AT_SYMBOL)) {
        setErrorInvalidFormat()
    } else if (inputString.length < EMAIL_MINIMUM_LENGTH) {
        setErrorShortEmail()
    } else {
        clearError()
        buttonEnabler()
    }

}

/**
 * Checks if inputString satisfies minimum password requirements
 * regarding length and format in order stop user from proceeding.
 * If those requirements are not satisfied error functions are being called,
 * otherwise functions clearError and buttonEnabler are being called
 */
fun passwordValidator(
    inputString: String,
    setErrorShortPassword: () -> Unit,
    setErrorInvalidFormat: () -> Unit,
    clearError: () -> Unit,
    buttonEnabler: () -> Unit
) {
    if (inputString.length < PASSWORD_MINIMUM_LENGTH) {
        setErrorShortPassword()
    } else if (!inputString.matches(Regex(CONTAINS_NUMBER_PATTERN))) {
        setErrorInvalidFormat()
    } else {
        clearError()
        buttonEnabler()
    }
}


/**
 * Function that encapsulates adding textChangedListener in order to compare
 * user entered password between two text input fields.
 * If user input is different between input fields setErrorNotMatchingPasswords function
 * is being called, functions clearError and buttonEnabler are being called otherwise
 */
fun TextInputEditText.addRepeatPasswordValidator(
    other: TextInputEditText,
    setErrorNotMatchingPasswords: () -> Unit,
    clearError: () -> Unit,
    buttonEnabler: () -> Unit
) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s.toString() != other.text.toString()) {
                setErrorNotMatchingPasswords()
            } else {
                clearError()
                buttonEnabler()
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    })
}