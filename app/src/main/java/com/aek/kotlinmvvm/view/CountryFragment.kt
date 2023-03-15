package com.aek.kotlinmvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aek.kotlinmvvm.databinding.FragmentCountryBinding
import com.bumptech.glide.Glide

class CountryFragment : Fragment() {

    private lateinit var binding: FragmentCountryBinding
    private var countryArgs: CountryFragmentArgs? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryArgs = CountryFragmentArgs.fromBundle(it)
        }

        with(binding) {
            Glide.with(this@CountryFragment)
                .load(countryArgs?.flag)
                .into(imageView)

            textViewName.text = countryArgs?.name
            textViewCapital.text = countryArgs?.capital
            textViewCurrency.text = countryArgs?.currency
            textViewRegion.text = countryArgs?.region
            textViewLanguage.text = countryArgs?.language
        }
    }
}
