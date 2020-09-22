package com.example.hcassessment.core.custom.weather_details

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.hcassessment.R
import com.example.hcassessment.core.data.pojo.group.WeatherItem
import kotlinx.android.synthetic.main.view_weather_details.view.*
import java.lang.Exception

class WeatherDetailsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), WeatherDetailsViewInterface {

    override var temperature: Double? = null
        set(value) {
            field = value
            value?.let {
                tvTemp?.text = context.getString(R.string.format_temp, it)
            }
        }

    override var temperatureMax: Double? = null
        set(value) {
            field = value
            value?.let {
                tvTempRange?.text = context.getString(R.string.format_temp_range, it , temperatureMin?:0.0)
            }
        }

    override var temperatureMin: Double? = null
        set(value) {
            field = value
            value?.let {
                tvTempRange?.text = context.getString(R.string.format_temp_range,  temperatureMax?:0.0 , it)
            }
        }

    override var name: String? = null
        set(value) {
            field = value
            value?.let {
                tvName?.text = it
            }
        }

    override var weather: String? = null
        set(value) {
            field = value
            value?.let {
                tvWeather?.text = it
            }
        }

    override var isFavorite: Boolean = false
        set(value) {
            field = value
            ivFavorite.setImageDrawable(
                ContextCompat
                    .getDrawable(
                        context,
                        if (value) R.drawable.ic_favorite_active else R.drawable.ic_favorite_inactive
                    )
            )
        }

    init {

        inflate(context, R.layout.view_weather_details, this)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WeatherDetailsView)

        try {
            if (typedArray.hasValue(R.styleable.WeatherDetailsView_wdv_temperature))
                temperature =
                    typedArray.getFloat(R.styleable.WeatherDetailsView_wdv_temperature, 0.0F)
                        .toDouble()

            if (typedArray.hasValue(R.styleable.WeatherDetailsView_wdv_temperatureMax))
                temperatureMax =
                    typedArray.getFloat(R.styleable.WeatherDetailsView_wdv_temperatureMax, 0.0F)
                        .toDouble()

            if (typedArray.hasValue(R.styleable.WeatherDetailsView_wdv_temperatureMin))
                temperatureMin =
                    typedArray.getFloat(R.styleable.WeatherDetailsView_wdv_temperatureMin, 0.0F)
                        .toDouble()

            if (typedArray.hasValue(R.styleable.WeatherDetailsView_wdv_name))
                name = typedArray.getString(R.styleable.WeatherDetailsView_wdv_name)


            if (typedArray.hasValue(R.styleable.WeatherDetailsView_wdv_weather))
                weather = typedArray.getString(R.styleable.WeatherDetailsView_wdv_weather)

            if (typedArray.hasValue(R.styleable.WeatherDetailsView_wdv_isFavorite))
                isFavorite =
                    typedArray.getBoolean(R.styleable.WeatherDetailsView_wdv_isFavorite, false)

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            typedArray.recycle()
        }
    }

    fun onFavoriteClick(listener: OnClickListener){
        ivFavorite.setOnClickListener(listener)
    }
}