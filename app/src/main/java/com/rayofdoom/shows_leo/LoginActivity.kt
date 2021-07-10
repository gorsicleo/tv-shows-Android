package com.rayofdoom.shows_leo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import com.google.android.material.textfield.TextInputLayout
import com.rayofdoom.shows_leo.databinding.ActivityLoginBinding


var conditionEmail = false
var conditionPass = false

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginButton.isClickable = false //BUTTON SHOULD BE DISABLED AT START
        binding.emailInput.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val inputString = binding.emailInput.text
                validateEmail(inputString.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        binding.passwordInput.addTextChangedListener(object :TextWatcher{
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



    private fun validateEmail(inputString: String){
        if (!inputString.contains("@")) setErrorEmail("Email address is not valid because @ is missing!")
        else if(inputString.length < 2) setErrorEmail("Email must have at least 2 characters!")
        else {
            conditionEmail=true
            enableButton()
        }

    }

    private fun validatePass(inputString: String){
        if (inputString.length<6) setErrorPass("Password is weak. Minimum of 6 characters")
        else if (!inputString.matches(Regex(".*\\d.*"))) setErrorPass("Password should contain number") //NOT WORKING!!
        else {
            conditionPass=true
            enableButton()
        }
    }

    private fun setErrorEmail(msg: String){
        conditionEmail=false
        binding.loginButton.isClickable = false
        binding.loginButton.setBackgroundColor(Color.parseColor("#4EBBBBBB"))
        binding.emailInput.error = msg

    }

    private fun setErrorPass(msg: String){
        conditionPass = false
        binding.loginButton.isClickable = false
        binding.loginButton.setBackgroundColor(Color.parseColor("#4EBBBBBB"))
        binding.passwordInput.error = msg

    }
    private fun enableButton(){
        if (conditionEmail && conditionPass){
        binding.loginButton.isClickable=true
        binding.loginButton.setBackgroundColor(Color.WHITE)
        binding.loginButton.setTextColor(Color.parseColor("#52368C"))
        }
        binding.loginButton.setOnClickListener{
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setMessage("USPJEH").show()

        }
    }
}