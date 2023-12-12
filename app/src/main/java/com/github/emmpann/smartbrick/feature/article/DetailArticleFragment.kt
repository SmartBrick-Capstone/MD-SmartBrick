package com.github.emmpann.smartbrick.feature.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.databinding.FragmentCameraBinding
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
    }

    private fun setupObserver() {
        viewModel.setArticleId(DetailArticleFragmentArgs.fromBundle(arguments as Bundle).articleId)
        viewModel.article?.observe(viewLifecycleOwner) {
            when (it) {
                is ResultApi.Success -> {
                    with(binding) {
                        tvDetailArticleTitle.text = it.data?.title
                        tvDetailArticleDesc.text = it.data?.description
                        tvDetailArticleDate.text = it.data?.date
                        Glide.with(requireContext()).load(it.data?.photoUrl).into(ivDetailArticle)
                    }
//                    showLoading(false)
                }

                is ResultApi.Loading -> {
//                    showLoading(true)
                }

                is ResultApi.Error -> {
//                    showLoading(false)
                }
            }
        }
    }


}