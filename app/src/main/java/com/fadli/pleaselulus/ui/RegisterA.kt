package com.fadli.pleaselulus.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.fadli.pleaselulus.data.ResultData
import com.fadli.pleaselulus.databinding.ActivityRegisterBinding
import com.fadli.pleaselulus.model.RegisterV
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterA : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityRegisterBinding
    private val registerV: RegisterV by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.register.setOnClickListener(this)
        binding.back.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == binding.register.id){
            closeKeyboard()
            loading(true)
            val username = binding.username.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            registerV.userRegister(username, email, password).observe(this) { registerResponse ->

                when(registerResponse){
                    is ResultData.Loading -> {
                        closeKeyboard()
                        loading(true)
                    }
                    is ResultData.Success -> {
                        loading(false)
                        Toast.makeText(this, "Registration Success", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else ->{
                        loading(false)
                        if (registerResponse.message.equals("Registration Failed")){
                            binding.register.error = registerResponse.message
                        }
                        Toast.makeText(this, "Email has been Taken, Please User Another Email", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        if(v.id == binding.back.id){
            startActivity(Intent(this, LoginA::class.java))
        }
    }

    private fun closeKeyboard(){
        val view: View? = this.currentFocus
        if(view != null){
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun loading(isLoading: Boolean) {
        if(isLoading) binding.pbarLogin.visibility = View.VISIBLE
        else binding.pbarLogin.visibility = View.GONE
    }

}