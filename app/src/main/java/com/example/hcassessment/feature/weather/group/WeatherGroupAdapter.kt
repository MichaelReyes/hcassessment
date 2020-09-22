package com.example.hcassessment.feature.weather.group

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hcassessment.R
import com.example.hcassessment.core.data.pojo.group.WeatherGroup
import com.example.hcassessment.core.utils.AutoUpdatableAdapter
import com.example.hcassessment.databinding.ItemWeatherGroupBinding
import javax.inject.Inject
import kotlin.properties.Delegates

class WeatherGroupAdapter @Inject constructor(val context: Context) :
    RecyclerView.Adapter<WeatherGroupAdapter.Holder>(),
    AutoUpdatableAdapter {

    internal var collection: List<WeatherGroup> by Delegates.observable(emptyList()) { prop, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id
        }
    }


    internal var clickListener: (WeatherGroup) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder.from(parent, R.layout.item_weather_group)

    override fun getItemCount() = collection.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.apply{
            item = collection[position]
            executePendingBindings()
            holder.itemView.setOnClickListener { clickListener(collection[position]) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class Holder(val binding: ItemWeatherGroupBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup, layout: Int): Holder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil.inflate<ItemWeatherGroupBinding>(inflater, layout, parent, false)
                return Holder(binding)
            }
        }
    }
}
