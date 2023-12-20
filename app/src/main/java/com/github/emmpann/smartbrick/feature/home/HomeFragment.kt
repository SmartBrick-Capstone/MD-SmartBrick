package com.github.emmpann.smartbrick.feature.home

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.core.data.remote.response.Article
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var bannerAdapter: BannerAdapter

    private var currentPosition = 0
    private val delay: Long = 3000 // Delay in milliseconds for auto-scrolling

    private lateinit var autoScrollJob: Job

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
        setClickListener()
    }

    private fun setupObserver() {
        viewModel.currentUsername.observe(viewLifecycleOwner) {
            binding.tvName.text = String.format(getString(R.string.home_welcome), it)
        }

        viewModel.article.observe(viewLifecycleOwner) {
            when (it) {
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

    private fun setupRecyclerView() {
        binding.rvArticle.overScrollMode = View.OVER_SCROLL_NEVER
        binding.rvArticle.layoutManager = LinearLayoutManager(requireContext())
        articleAdapter = ArticleAdapter()
        binding.rvArticle.adapter = articleAdapter
        articleAdapter.setOnItemClickCallback(object : ArticleAdapter.OnItemClickCallback {
            override fun onItemClicked(article: Article) {
                val toDetailArticle =
                    HomeFragmentDirections.actionHomeFragmentToDetailArticleFragment()
                toDetailArticle.articleSlug = article.slug
                findNavController().navigate(toDetailArticle)
            }
        })

        binding.rvToday.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bannerAdapter = BannerAdapter()
        binding.rvToday.adapter = bannerAdapter
        bannerAdapter.setOnItemClickCallback(object : BannerAdapter.OnItemClickCallback {
            override fun onItemClicked(article: Article) {
                val toDetailArticle =
                    HomeFragmentDirections.actionHomeFragmentToDetailArticleFragment()
                toDetailArticle.articleSlug = article.slug
                findNavController().navigate(toDetailArticle)
            }
        })

        autoScrollJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(delay)
                if (currentPosition == bannerAdapter.itemCount) {
                    currentPosition = 0
                }
                binding.rvToday.smoothScrollToPosition(++currentPosition)
            }
        }
    }

    private fun setClickListener() {
        with(binding) {
            val toTipsTricks = HomeFragmentDirections.actionHomeFragmentToTipsTricksFragment()
            card1.setOnClickListener {
                toTipsTricks.tipstricksId = "1"
                findNavController().navigate(toTipsTricks)
            }

            card2.setOnClickListener {
                toTipsTricks.tipstricksId = "2"
                findNavController().navigate(toTipsTricks)
            }

            card3.setOnClickListener {
                toTipsTricks.tipstricksId = "3"
                findNavController().navigate(toTipsTricks)
            }

            card4.setOnClickListener {
                toTipsTricks.tipstricksId = "4"
                findNavController().navigate(toTipsTricks)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.bannerShimmer.startShimmer()
            binding.articleShimer.startShimmer()
        } else {
            binding.bannerShimmer.visibility = View.INVISIBLE
            binding.articleShimer.visibility = View.INVISIBLE
            binding.bannerShimmer.stopShimmer()
            binding.articleShimer.stopShimmer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        autoScrollJob.cancel() // Cancel the coroutine job when the activity is destroyed
    }
}