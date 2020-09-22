package com.example.hcassessment.feature.weather.group

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hcassessment.R
import com.example.hcassessment.core.data.pojo.group.WeatherItem
import com.example.hcassessment.core.utils.AutoUpdatableAdapter
import com.example.hcassessment.core.utils.Constants
import com.example.hcassessment.databinding.ItemWeatherGroupBinding
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.view_weather_group.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class WeatherGroupAdapter @Inject constructor(val context: Context) :
    RecyclerView.Adapter<WeatherGroupAdapter.Holder>(),
    AutoUpdatableAdapter {

    internal var collection: List<WeatherItem> by Delegates.observable(emptyList()) { prop, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id }
    }


    internal var clickListener: (WeatherItem) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder.from(parent, R.layout.item_weather_group)

    override fun getItemCount() = collection.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.apply{
            val wg = collection[position]
            item = wg

            holder.itemView.setOnClickListener { clickListener(wg) }
            layoutWgv.ivFavorite.setOnClickListener {
                val isFav = !wg.isFavorite
                wg.isFavorite = isFav

                Hawk.get(Constants.PREF_KEY_GROUP_FAVORITES, mutableListOf<WeatherItem>()).apply {
                    if(isFav)
                        this.add(wg)
                    else
                        this.removeAll { it.id == wg.id }

                    Hawk.put(Constants.PREF_KEY_GROUP_FAVORITES, this)
                }

                notifyItemChanged(position)
            }

            wg.isFavorite = Hawk.get(Constants.PREF_KEY_GROUP_FAVORITES, mutableListOf<WeatherItem>()).any { it.id == wg.id }

            executePendingBindings()
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
