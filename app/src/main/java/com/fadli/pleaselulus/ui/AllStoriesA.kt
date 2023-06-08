package com.fadli.pleaselulus.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadli.pleaselulus.R
import com.fadli.pleaselulus.adapter.LoadingAdapter
import com.fadli.pleaselulus.adapter.OnItemClickCallback
import com.fadli.pleaselulus.adapter.StoriesAdapter
import com.fadli.pleaselulus.databinding.ActivityAllStoriesBinding
import com.fadli.pleaselulus.model.StoriesV
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllStoriesA :AppCompatActivity(){

    private lateinit var binding: ActivityAllStoriesBinding
    private val storiesV: StoriesV by viewModels()
    @Inject lateinit var storiesAdapter: StoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllStoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loading(true)
        supportActionBar?.title = "Fadli App Story"
        showRecyclerView()
        storiesV.showAllStories()
        showData()


        binding.addStory.setOnClickListener{
            startActivity(Intent(this, AddStoriesA::class.java))
        }
    }

    private fun showData(){
        storiesV.showAllStories().observe(this) { storiesResponse ->
            storiesAdapter.submitData(lifecycle, storiesResponse)
            storiesAdapter.addLoadStateListener { listenerStories ->
                if (listenerStories.refresh != LoadState.Loading){
                    loading(false)
                }
                if (listenerStories.refresh is LoadState.Error){
                    val data = listenerStories.refresh as LoadState.Error
                    if (data.error.message.equals("HTTP 401 Unauthorized")){
                        Toast.makeText(this@AllStoriesA, "Token Expired, Please Login Again", Toast.LENGTH_SHORT).show()
                        val loginIntent = Intent(this@AllStoriesA, LoginA::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        PreferenceManager.getDefaultSharedPreferences(this@AllStoriesA).edit().clear().apply()
                        startActivity(loginIntent)
                        finish()
                    }
                    else{
                        binding.loadError.isVisible = true
                        binding.btnReload.setOnClickListener {
                            binding.loadError.isVisible = false
                            showData()
                        }

                    }

                }
            }
        }
    }

    private fun showRecyclerView(){
        storiesAdapter = StoriesAdapter()
        with(binding){
            rvStories.layoutManager = LinearLayoutManager(this@AllStoriesA)
            rvStories.setHasFixedSize(true)
            rvStories.adapter = storiesAdapter.withLoadStateFooter(
                LoadingAdapter{
                    storiesAdapter.retry()
                }
            )
        }

        storiesAdapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(id: String) {
                Intent(this@AllStoriesA, DetailStoryA::class.java).also {
                    it.putExtra(DetailStoryA.EXTRA_ID, id)
                    startActivity(it)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (intent.extras != null){
            val restart = intent.getBooleanExtra("reload", false)
            if (restart){
                storiesV.showAllStories()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.Logout){
            PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply()
            val intent = Intent(this, LoginA::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        if(item.itemId == R.id.maps){
            startActivity(Intent(this, MapsA::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loading(isLoading: Boolean) {
        if(isLoading) binding.pbarLogin.visibility = View.VISIBLE
        else binding.pbarLogin.visibility = View.GONE
    }
}