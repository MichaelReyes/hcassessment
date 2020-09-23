package com.example.hcassessment.feature.weather.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.hcassessment.R
import com.example.hcassessment.core.base.App
import com.example.hcassessment.core.base.BaseActivity
import com.example.hcassessment.core.base.BaseFragment
import com.example.hcassessment.core.extensions.observe
import com.example.hcassessment.core.extensions.viewModel
import com.example.hcassessment.databinding.FragmentWeatherGroupBinding
import com.example.hcassessment.feature.weather.details.WeatherDetailsFragment
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.banner_no_network.*
import kotlinx.android.synthetic.main.list_group.*
import javax.inject.Inject

class WeatherGroupFragment : BaseFragment<FragmentWeatherGroupBinding>() {

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var vm: WeatherGroupViewModel

    @Inject
    lateinit var adapter: WeatherGroupAdapter

    override val layoutRes = R.layout.fragment_weather_group

    private var listSkeleton: Skeleton? = null

    override fun onCreated(savedInstance: Bundle?) {
        (activity as? BaseActivity<*>)?.setToolbar(show = true, showBackButton = false, title = "Weather Forecast")

        initObserver()
        initViews()
        fetchData()
    }

    private fun fetchData(){
        vm.fetchWeatherGroups(listOf("1701668", "3067696","1835848"))
    }

    private fun initViews(){
        rvWeatherGroups.adapter = adapter

        listSkeleton = rvWeatherGroups.applySkeleton(R.layout.item_weather_group, 5)

        srlWeatherGroups.setOnRefreshListener {
            srlWeatherGroups.isRefreshing = false
            fetchData()
        }

        adapter.clickListener = {
            findNavController()
                .navigate(R.id.action_open_details,
                    bundleOf(Pair(WeatherDetailsFragment.ARGS_WEATHER_DETAIL, gson.toJson(it)))
                )
        }
    }

    private fun initObserver(){
        vm = viewModel(viewModelFactory){
            observe(ui, ::handleUiUpdates)
            observe(groups){
                it?.run { adapter.collection = this }
                tvEmpty.visibility = if(it?.isNotEmpty() == true) View.GONE else View.VISIBLE
            }
        }

        activity?.apply {
            (application as App).internetConnectionStream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    clNoNetwork.visibility = if(it) View.GONE else View.VISIBLE
                    vm.hasInternetConnection.value = it
                }.addTo(disposables)
        }
    }

    private fun handleUiUpdates(ui: WeatherGroupUi?){
        ui?.apply {
            (activity as? BaseActivity<*>)?.showLoading(loading)

            if(loading)
                listSkeleton?.showSkeleton()
            else
                listSkeleton?.showOriginal()

            error?.apply {
                showMessage(this, false)
                vm.setError(null)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }
}