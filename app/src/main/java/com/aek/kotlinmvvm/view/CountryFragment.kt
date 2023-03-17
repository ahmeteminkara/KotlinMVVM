package com.aek.kotlinmvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aek.kotlinmvvm.databinding.FragmentCountryBinding
import com.aek.kotlinmvvm.util.extentions.loadUrl
import com.aek.kotlinmvvm.viewmodel.CountryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryFragment : Fragment() {

    private lateinit var binding: FragmentCountryBinding
    private var countryArgs: CountryFragmentArgs? = null
    private lateinit var viewModel: CountryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[CountryViewModel::class.java]
        return binding.root
    }

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
}
