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
import com.github.emmpann.smartbrick.core.data.remote.response.Article
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.databinding.FragmentHistoryBinding
import com.github.emmpann.smartbrick.feature.home.ArticleAdapter
import com.github.emmpann.smartbrick.feature.home.HomeFragmentDirections
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
//        historyAdapter.setOnItemClickCallback(object : ArticleAdapter.OnItemClickCallback {
//            override fun onItemClicked(article: Article) {
//                val toDetailArticle =
//                    HomeFragmentDirections.actionHomeFragmentToDetailArticleFragment()
//                toDetailArticle.articleSlug = article.slug
//                findNavController().navigate(toDetailArticle)
//            }
//        })
    }

    private fun setupObserver() {
        viewModel.history.observe(viewLifecycleOwner) {
            when (it) {
                is ResultApi.Success -> {
                    historyAdapter.submitList(it.data)
                }

                is ResultApi.Loading -> {

                }

                is ResultApi.Error -> {

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