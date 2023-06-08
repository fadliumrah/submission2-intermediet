@file:Suppress("DEPRECATION")

package com.fadli.pleaselulus.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.fadli.pleaselulus.R
import com.fadli.pleaselulus.constanta.TOKEN
import com.fadli.pleaselulus.custom.CustomInputPassword
import com.fadli.pleaselulus.data.ResultData
import com.fadli.pleaselulus.databinding.ActivityLoginBinding
import com.fadli.pleaselulus.model.LoginV
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginA : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private val loginV: LoginV by viewModels()
    private lateinit var inputPassword: CustomInputPassword

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputPassword = findViewById(R.id.password)

        playAnimation()

        supportActionBar?.hide()
        binding.btnRegister.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)

    }
    
    override fun onClick(v: View) {
        if(v.id == binding.btnRegister.id){
            val moveIntent = Intent(this@LoginA, RegisterA::class.java)
            startActivity(moveIntent)
        }
        if (v.id == binding.btnLogin.id){
            val email = binding.username.text.toString().trim()
            val password = inputPassword.text.toString().trim()

            loginV.userLogin(email, password).observe(this){ loginResponse ->
                when(loginResponse) {
                    is ResultData.Loading -> {
                        closeKeyboard()
                        loading(true)
                    }
                    is ResultData.Success -> {
                        loading(false)
                        val token = loginResponse.data?.loginUserResult?.token
                        if (token.isNullOrBlank()) {
                            Toast.makeText(this@LoginA, "Failure Login", Toast.LENGTH_SHORT)
                                .show()
                            return@observe
                        }
                        PreferenceManager.getDefaultSharedPreferences(this@LoginA)
                            .edit()
                            .putString(TOKEN, token)
                            .apply()
                        val loginIntent = Intent(this@LoginA, AllStoriesA::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(loginIntent)
                        finish()
                    }
                    else -> {
                        loading(false)

                        Toast.makeText(
                            this@LoginA,
                            loginResponse.message ?: "Failure Login",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun closeKeyboard(){
        val view: View? = this.currentFocus
        if(view != null){
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun playAnimation(){
        val title1 = ObjectAnimator.ofFloat(binding.login, View.TRANSLATION_Y, 200f, 0f).setDuration(2000)
        val title2 = ObjectAnimator.ofFloat(binding.login, View.ALPHA, 1f).setDuration(2000)
        val email1 = ObjectAnimator.ofFloat(binding.username, View.TRANSLATION_X, 200f, 0f).setDuration(2000)
        val email2 = ObjectAnimator.ofFloat(binding.username, View.ALPHA, 1f).setDuration(2000)
        val password1 = ObjectAnimator.ofFloat(binding.password, View.TRANSLATION_X, 200f, 0f).setDuration(2000)
        val password2 = ObjectAnimator.ofFloat(binding.password, View.ALPHA, 1f).setDuration(2000)
        val signin1 = ObjectAnimator.ofFloat(binding.btnLogin, View.TRANSLATION_X, 200f, 0f).setDuration(2000)
        val signin2 = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(2000)
        val create = ObjectAnimator.ofFloat(binding.create, View.ALPHA, 1f).setDuration(2000)
        val regis1 = ObjectAnimator.ofFloat(binding.btnRegister, View.TRANSLATION_X, 200f, 0f).setDuration(2000)
        val regis2 = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(2000)

        AnimatorSet().apply {
            playTogether(title1, title2)
            playTogether(email1, email2)
            playTogether(password1, password2)
            playTogether(signin1, signin2)
            playSequentially(create)
            playTogether(regis1, regis2)
            start()
        }
    }


    private fun loading(isLoading: Boolean) {
        if(isLoading) binding.pbarLogin.visibility = View.VISIBLE
        else binding.pbarLogin.visibility = View.GONE
    }
}