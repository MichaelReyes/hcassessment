package com.example.hcassessment.feature.weather.details

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hcassessment.BuildConfig
import com.example.hcassessment.R
import com.example.hcassessment.core.data.pojo.group.WeatherItem
import com.example.hcassessment.core.network.WeatherRepository
import com.example.hcassessment.core.utils.Constants
import com.example.hcassessment.core.utils.CustomLiveData
import com.orhanobut.hawk.Hawk
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class WeatherDetailsViewModel @Inject constructor(
    private val context: Context,
    private val repo: WeatherRepository
): ViewModel() {

    private val disposables = CompositeDisposable()

    var hasInternetConnection = MutableLiveData(true)

    val ui = CustomLiveData<WeatherDetailsUi>()
    val weather = MutableLiveData<WeatherItem>()

    init {
        ui.value = WeatherDetailsUi()
    }

    fun setWeatherDetails(value: WeatherItem){
        value.isFavorite =
            Hawk.get(Constants.PREF_KEY_GROUP_FAVORITES, mutableListOf<WeatherItem>()).any { it.id == value.id }
        weather.value = value
    }

    fun setError(value: String?){
        ui.value?.error = value
    }

    fun fetchDetails(){
        weather.value?.apply {
            repo.getWeatherDetails(name)
                .doOnSubscribe { ui.value?.loading = true }
                .subscribe({
                    setWeatherDetails(it)
                    ui.value?.loading = false
                },{
                    if(BuildConfig.DEBUG)
                        it.printStackTrace()
                    setError(it.localizedMessage?:context.getString(R.string.error_generic))
                    ui.value?.loading = false
                })
        }
    }

    fun onToggleFavorite(){
        weather.value?.let {item ->
            val isFav = !item.isFavorite
            item.isFavorite = isFav
            Hawk.get(Constants.PREF_KEY_GROUP_FAVORITES, mutableListOf<WeatherItem>()).apply {
                if(isFav)
                    this.add(item)
                else
                    this.removeAll { it.id == item.id }

                Hawk.put(Constants.PREF_KEY_GROUP_FAVORITES, this)
            }
            weather.value = item
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}