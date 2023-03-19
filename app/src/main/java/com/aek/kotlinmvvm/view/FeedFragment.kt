package com.aek.kotlinmvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.aek.kotlinmvvm.adapter.CountryAdapter
import com.aek.kotlinmvvm.core.base.BaseFragmentWithViewModel
import com.aek.kotlinmvvm.databinding.FragmentFeedBinding
import com.aek.kotlinmvvm.viewmodel.FeedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : BaseFragmentWithViewModel<FragmentFeedBinding, FeedViewModel>(
    FeedViewModel::class.java
) {

    private lateinit var adapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]

        adapter = CountryAdapter {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(it.id)
            binding.root.findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeViewModel()
        viewModel.getData()
    }

    private fun initView() {
        with(binding) {
            recyclerViewCountry.adapter = adapter
            swipeRefreshLayout.setOnRefreshListener {
                adapter.clear()
                viewModel.refreshData()
            }
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            loadingLiveData.observe(viewLifecycleOwner) {
                binding.swipeRefreshLayout.isRefreshing = it
            }

            countriesLiveData.observe(viewLifecycleOwner) {
                binding.recyclerViewCountry.isVisible = true
                adapter.updateList(it)
            }

            errorLiveData.observe(viewLifecycleOwner) {
                binding.recyclerViewCountry.isVisible = false
                showAlertDialog(it)
            }
        }
    }

    override fun getViewBinding(inflater: LayoutInflater, parent: ViewGroup?): FragmentFeedBinding {
        return FragmentFeedBinding.inflate(inflater, parent, false)
    }
}
