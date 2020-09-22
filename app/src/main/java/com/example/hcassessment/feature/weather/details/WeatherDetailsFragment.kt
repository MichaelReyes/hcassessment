package com.example.hcassessment.feature.weather.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.hcassessment.R
import com.example.hcassessment.core.base.App
import com.example.hcassessment.core.base.BaseActivity
import com.example.hcassessment.core.base.BaseFragment
import com.example.hcassessment.core.data.pojo.group.WeatherItem
import com.example.hcassessment.core.extensions.observe
import com.example.hcassessment.core.extensions.viewModel
import com.example.hcassessment.databinding.FragmentWeatherDetailsBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.banner_no_network.*
import kotlinx.android.synthetic.main.fragment_weather_details.*
import kotlinx.android.synthetic.main.view_weather_details.view.*
import javax.inject.Inject


class WeatherDetailsFragment : BaseFragment<FragmentWeatherDetailsBinding>() {

    companion object{
        const val ARGS_WEATHER_DETAIL = "a_weather_detail"
    }

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var vm: WeatherDetailsViewModel

    override val layoutRes = R.layout.fragment_weather_details

    override fun onCreated(savedInstance: Bundle?) {

        (activity as? BaseActivity<*>)?.setToolbar(show = true, showBackButton = true, title = "Weather Forecast")

        initViews()
        initObservable()
        checkArgs()
    }

    private fun checkArgs() {
        arguments?.apply {
            if(containsKey(ARGS_WEATHER_DETAIL)) {
                vm.setWeatherDetails(
                    gson.fromJson(
                        getString(ARGS_WEATHER_DETAIL),
                        WeatherItem::class.java
                    )
                )
                vm.fetchDetails()
            }
        }
    }

    private fun initObservable() {
        vm = viewModel(viewModelFactory){
            observe(ui, ::handleUiUpdates)
        }

        binding.lifecycleOwner = this
        binding.vm = vm
    }

    private fun handleUiUpdates(weatherDetailsUi: WeatherDetailsUi?) {
        weatherDetailsUi?.apply {
            (activity as? BaseActivity<*>)?.showLoading(loading)

            error?.apply {
                showMessage(this, false)
                vm.setError(null)
            }
        }
    }

    private fun initViews() {
        activity?.apply {
            (application as App).internetConnectionStream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    clNoNetwork.visibility = if(it) View.GONE else View.VISIBLE
                    vm.hasInternetConnection.value = it
                }.addTo(disposables)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }
}