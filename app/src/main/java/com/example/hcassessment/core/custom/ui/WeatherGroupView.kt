package com.example.hcassessment.core.custom.ui

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.hcassessment.R
import kotlinx.android.synthetic.main.view_weather_group.view.*
import java.lang.Exception

class WeatherGroupView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), WeatherGroupViewInterface {

    override var temperature: Double? = null
        set(value) {
            field = value
            value?.let {
                tvTemp?.text = context.getString(R.string.format_temp, it)

                when {
                    it < 1 -> clParent.setBackgroundColor(
                        ContextCompat.getColor(context, R.color.colorWeatherFreezing)
                    )
                    it in 1.0..15.0 -> clParent.setBackgroundColor(
                        ContextCompat.getColor(context, R.color.colorWeatherCold)
                    )
                    it in 15.0..30.0 -> clParent.setBackgroundColor(
                        ContextCompat.getColor(context, R.color.colorWeatherWarm)
                    )
                    else -> clParent.setBackgroundColor(
                        ContextCompat.getColor(context, R.color.colorWeatherHot)
                    )
                }
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

        inflate(context, R.layout.view_weather_group, this)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WeatherGroupView)

        try {
            if (typedArray.hasValue(R.styleable.WeatherGroupView_wgv_temperature))
                temperature =
                    typedArray.getFloat(R.styleable.WeatherGroupView_wgv_temperature, 0.0F)
                        .toDouble()


            if (typedArray.hasValue(R.styleable.WeatherGroupView_wgv_name))
                name = typedArray.getString(R.styleable.WeatherGroupView_wgv_name)


            if (typedArray.hasValue(R.styleable.WeatherGroupView_wgv_weather))
                weather = typedArray.getString(R.styleable.WeatherGroupView_wgv_weather)

            if (typedArray.hasValue(R.styleable.WeatherGroupView_wgv_isFavorite))
                isFavorite =
                    typedArray.getBoolean(R.styleable.WeatherGroupView_wgv_isFavorite, false)

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            typedArray.recycle()
        }
    }

}