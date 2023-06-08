package com.fadli.pleaselulus.ui

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fadli.pleaselulus.data.ResultData
import com.fadli.pleaselulus.databinding.ActivityDetailStoryBinding
import com.fadli.pleaselulus.model.DetailV
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailStoryA : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    private val detailV: DetailV by viewModels()

    private var id: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Stories"
        loading(true)


        if (intent.extras != null){
            id = intent.getStringExtra(EXTRA_ID).toString()
        }

        viewDetailStory()
    }

    @Suppress("DEPRECATION")
    private fun viewDetailStory() {
        detailV.getDetailStories(id).observe(this){ detailResponse ->
            when(detailResponse) {
                is ResultData.Loading ->{
                    loading(true)
                }
                is ResultData.Success ->{
                    loading(false)
                    with(binding){
                        Glide.with(this@DetailStoryA)
                            .load(detailResponse.data?.dataStory?.photoUrl)
                            .into(userImage)
                        idUser.text = detailResponse.data?.dataStory?.name
                        description.text = detailResponse.data?.dataStory?.description
                    }
                }
                else ->{
                    loading(false)
                    if (detailResponse.code == 401){
                        PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply()
                        val intentLogin = Intent(this@DetailStoryA, LoginA::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intentLogin)
                        finish()

                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loading(isLoading: Boolean) {
        if(isLoading) binding.pbarLogin.visibility = View.VISIBLE
        else binding.pbarLogin.visibility = View.GONE
    }

    companion object{
        const val EXTRA_ID = "extra_name"
    }

}