package com.github.emmpann.smartbrick.feature.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListener()
        setupObserver()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvHistory.overScrollMode = View.OVER_SCROLL_NEVER
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager(requireContext()).orientation
            )
        )
        historyAdapter = HistoryAdapter()
        binding.rvHistory.adapter = historyAdapter
    }

    private fun setupObserver() {
        viewModel.isVerified.observe(viewLifecycleOwner) { isVerified ->
            if (isVerified) {
                viewModel.history.observe(viewLifecycleOwner) {
                    when (it) {
                        is ResultApi.Success -> {
                            with(binding) {
                                rvHistory.visibility = View.GONE
                                tvUnverifiedAccount.visibility = View.VISIBLE
                                tvUnverifiedAccount.text = getString(R.string.feature_is_under_construction)
                            }
                        }

                        is ResultApi.Loading -> {

                        }

                        is ResultApi.Error -> {

                        }
                    }
                }
            } else {
                with(binding) {
                    rvHistory.visibility = View.GONE
                    tvUnverifiedAccount.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setClickListener() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}