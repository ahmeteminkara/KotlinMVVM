package com.aek.kotlinmvvm.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aek.kotlinmvvm.databinding.ItemCountryRowBinding
import com.aek.kotlinmvvm.model.Country
import com.bumptech.glide.Glide

class CountryAdapter(
    private val onSelectCountry: (country: Country) -> Unit
) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    private val list = arrayListOf<Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCountryRowBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        with(holder.viewBinding) {
            Glide.with(root).load(data.flag).into(imageView)
            textViewName.text = data.name
            textViewRegion.text = data.region

            root.setOnClickListener {
                onSelectCountry.invoke(data)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Country>) {
        list.addAll(newList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(val viewBinding: ItemCountryRowBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}
