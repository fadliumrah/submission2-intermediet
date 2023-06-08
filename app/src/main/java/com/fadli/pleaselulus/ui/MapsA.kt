package com.fadli.pleaselulus.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.fadli.pleaselulus.R
import com.fadli.pleaselulus.data.ResultData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.fadli.pleaselulus.databinding.ActivityMapsBinding
import com.fadli.pleaselulus.model.MapsV
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsA : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding : ActivityMapsBinding
    private lateinit var googleMap: GoogleMap
    private val mapsV: MapsV by viewModels()
    private val boundBuilder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val maps = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        maps.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isIndoorLevelPickerEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true
        googleMap.uiSettings.isCompassEnabled = true
        getStoriesLocation()
    }




    private fun getStoriesLocation(){

        mapsV.getStoriesLocation().observe(this){ mapResponse ->
            when(mapResponse){
                is ResultData.Loading ->{
                }
                is ResultData.Success -> {
                    mapResponse.data?.listDataStory?.forEach{
                        setMarker(LatLng(it.lat as Double, it.lon as Double), it.name as String)
                    }

                    val bounds: LatLngBounds = boundBuilder.build()
                    googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(
                            bounds,
                            resources.displayMetrics.widthPixels,
                            resources.displayMetrics.heightPixels,
                            300
                        )
                    )
                }
                else -> {
                    Toast.makeText(this@MapsA, mapResponse.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun setMarker(latLon: LatLng, creator: String){
        googleMap.addMarker(
            MarkerOptions()
                .position(latLon)
                .title(creator)
        )
        boundBuilder.include(latLon)
    }
}