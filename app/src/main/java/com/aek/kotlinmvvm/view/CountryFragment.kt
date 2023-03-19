package com.aek.kotlinmvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aek.kotlinmvvm.core.base.BaseFragmentWithViewModel
import com.aek.kotlinmvvm.databinding.FragmentCountryBinding
import com.aek.kotlinmvvm.util.extentions.loadUrl
import com.aek.kotlinmvvm.viewmodel.CountryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryFragment : BaseFragmentWithViewModel<FragmentCountryBinding, CountryViewModel>(
    CountryViewModel::class.java
) {

    private var countryArgs: CountryFragmentArgs? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle ->
            countryArgs = CountryFragmentArgs.fromBundle(bundle)
            viewModel.getCountryData(countryArgs?.countryId ?: 0)
        }

        viewModel.countryLiveData.observe(viewLifecycleOwner) {
            with(binding) {
                imageView.loadUrl(it?.flag)
                textViewName.text = it?.name
                textViewCapital.text = it?.capital
                textViewCurrency.text = it?.currency
                textViewRegion.text = it?.region
                textViewLanguage.text = it?.language
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentCountryBinding {
        return FragmentCountryBinding.inflate(inflater, parent, false)
    }
}
