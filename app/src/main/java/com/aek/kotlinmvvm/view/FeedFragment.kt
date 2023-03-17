package com.aek.kotlinmvvm.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.aek.kotlinmvvm.adapter.CountryAdapter
import com.aek.kotlinmvvm.databinding.FragmentFeedBinding
import com.aek.kotlinmvvm.viewmodel.FeedViewModel

class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var viewModel: FeedViewModel
    private lateinit var adapter: CountryAdapter
    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this)[FeedViewModel::class.java]
        alertDialog = AlertDialog.Builder(context)
            .setTitle("Error")
            .setPositiveButton("DISMISS") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        adapter = CountryAdapter {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(it.id)
            binding.root.findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeViewModel()

        context?.let { viewModel.getData(it) }
    }

    private fun initView() {
        with(binding) {
            recyclerViewCountry.adapter = adapter
            swipeRefreshLayout.setOnRefreshListener {
                adapter.clear()
                context?.let { viewModel.refreshData(it) }
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
                alertDialog.setMessage(it)
                alertDialog.show()
            }
        }
    }
}
