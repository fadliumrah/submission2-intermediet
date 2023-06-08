@file:Suppress("DEPRECATION")

package com.fadli.pleaselulus.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import com.fadli.pleaselulus.constanta.TOKEN
import com.fadli.pleaselulus.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainA : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed(
            {
                val token = PreferenceManager.getDefaultSharedPreferences(this@MainA).getString(
                    TOKEN, "")
                if (token != null && token.isNotEmpty()){
                    val intent = Intent(this@MainA, AllStoriesA::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    val intent = Intent(this@MainA, LoginA::class.java)
                    startActivity(intent)
                    finish()
                }
            }, 3000L)
    }
}