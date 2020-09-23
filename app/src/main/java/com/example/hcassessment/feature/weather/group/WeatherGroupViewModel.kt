package com.example.hcassessment.feature.weather.group

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hcassessment.BuildConfig
import com.example.hcassessment.R
import com.example.hcassessment.core.data.pojo.group.WeatherItem
import com.example.hcassessment.core.network.WeatherRepository
import com.example.hcassessment.core.utils.CustomLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class WeatherGroupViewModel @Inject constructor(
    private val context: Context,
    private val repo: WeatherRepository
): ViewModel() {

    private val disposables = CompositeDisposable()

    var hasInternetConnection = MutableLiveData(true)

    val ui = CustomLiveData<WeatherGroupUi>()
    val groups = MutableLiveData(listOf<WeatherItem>())

    init {
        ui.value = WeatherGroupUi()
    }

    fun setError(value: String?){
        ui.value?.error = value
    }

    fun fetchWeatherGroups(ids: List<String>){
        if(hasInternetConnection.value == true)
            repo.getWeatherGroup(
                ids.joinToString (separator = ",")
            ).doOnSubscribe { ui.value?.loading = true }
                .subscribe({
                    ui.value?.loading = false
                    groups.value = it.list
                },{
                    if(BuildConfig.DEBUG)
                        it.printStackTrace()
                    ui.value?.loading = false
                    setError(it?.localizedMessage?:context.getString(R.string.error_generic))
                }).addTo(disposables)
        else setError(context.getString(R.string.error_no_network))

    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}