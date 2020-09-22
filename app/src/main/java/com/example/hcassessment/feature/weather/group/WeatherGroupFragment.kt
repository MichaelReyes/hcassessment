package com.example.hcassessment.feature.weather.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hcassessment.R
import com.example.hcassessment.core.base.BaseFragment
import com.example.hcassessment.databinding.FragmentWeatherGroupBinding
import javax.inject.Inject

class WeatherGroupFragment : BaseFragment<FragmentWeatherGroupBinding>() {

    @Inject
    lateinit var vm: WeatherGroupViewModel



    override val layoutRes = R.layout.fragment_weather_group

    override fun onCreated(savedInstance: Bundle?) {

    }
}