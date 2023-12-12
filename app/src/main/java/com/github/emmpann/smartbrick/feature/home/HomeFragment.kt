package com.github.emmpann.smartbrick.feature.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.core.data.remote.response.Article
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var bannerAdapter: BannerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvArticle.layoutManager = LinearLayoutManager(requireContext())
        articleAdapter = ArticleAdapter()
        binding.rvArticle.adapter = articleAdapter
        articleAdapter.setOnItemClickCallback(object : ArticleAdapter.OnItemClickCallback {
            override fun onItemClicked(article: Article) {
                val toDetailArticle = HomeFragmentDirections.actionHomeFragmentToDetailArticleFragment()
                findNavController().navigate(toDetailArticle)
            }

        })

        binding.rvToday.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bannerAdapter = BannerAdapter()
        binding.rvToday.adapter = bannerAdapter
        bannerAdapter.setOnItemClickCallback(object : BannerAdapter.OnItemClickCallback {
            override fun onItemClicked(article: Article) {
                val toDetailArticle = HomeFragmentDirections.actionHomeFragmentToDetailArticleFragment()
                findNavController().navigate(toDetailArticle)
            }
        })
    }

    private fun setupObserver() {
        viewModel.currentUsername.observe(viewLifecycleOwner) {
            binding.tvName.text = String.format(getString(R.string.home_welcome), it)
        }

        viewModel.article.observe(viewLifecycleOwner) {
            when(it) {
                is ResultApi.Success -> {
                    articleAdapter.submitList(it.data.listArticle)
                    bannerAdapter.submitList(it.data.listArticle)
                    showLoading(false)
                }

                is ResultApi.Loading -> {
                    showLoading(true)
                }

                is ResultApi.Error -> {
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}