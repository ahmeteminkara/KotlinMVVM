package com.aek.kotlinmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aek.kotlinmvvm.core.base.BaseRecyclerViewAdapter
import com.aek.kotlinmvvm.databinding.ItemCountryRowBinding
import com.aek.kotlinmvvm.model.Country
import com.aek.kotlinmvvm.util.extentions.loadUrl

class CountryAdapter(
    private val onSelectCountry: (country: Country) -> Unit
) : BaseRecyclerViewAdapter<Country, ItemCountryRowBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemCountryRowBinding {
        return ItemCountryRowBinding.inflate(inflater, parent, false)
    }

    override fun setData(binding: ItemCountryRowBinding, item: Country, position: Int) {
        with(binding) {
            imageView.loadUrl(item.flag)
            textViewName.text = item.name
            textViewRegion.text = item.region

            root.setOnClickListener {
                onSelectCountry.invoke(item)
            }
        }
    }
}
