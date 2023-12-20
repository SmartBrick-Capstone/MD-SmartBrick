package com.github.emmpann.smartbrick.feature.article

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.databinding.FragmentDetailArticleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailArticleFragment : Fragment() {

    private lateinit var binding: FragmentDetailArticleBinding
    private val viewModel: DetailArticleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailArticleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setClickListener()
    }

    private fun setClickListener() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupObserver() {
        viewModel.setArticleSlug(DetailArticleFragmentArgs.fromBundle(arguments as Bundle).articleSlug)
        viewModel.article.observe(viewLifecycleOwner) {
            when (it) {
                is ResultApi.Success -> {
                    with(binding) {
                        tvDetailArticleTitle.text = it.data.listArticle.title
                        tvDetailArticleDesc.text =
                            Html.fromHtml(it.data.listArticle.content, Html.FROM_HTML_MODE_COMPACT)
                        tvDetailArticleDate.text = it.data.listArticle.date
                        Glide.with(requireContext()).load(it.data.listArticle.image)
                            .into(ivDetailArticle)
                    }
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
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}