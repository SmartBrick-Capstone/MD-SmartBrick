package com.github.emmpann.smartbrick.feature.tipstricks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.emmpann.smartbrick.R
import com.github.emmpann.smartbrick.databinding.FragmentTipsTricksBinding

class TipsTricksFragment : Fragment() {

    private lateinit var binding: FragmentTipsTricksBinding
    private val viewModel: TipsTricksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTipsTricksBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObserver()
        setClickListener()
    }

    private fun setClickListener() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupObserver() {
        when (TipsTricksFragmentArgs.fromBundle(arguments as Bundle).tipstricksId) {
            "1" -> {
                with(binding) {
                    ivTipstricks.setImageResource(R.drawable.ic_large_trashcan)
                    tvTipstricks.text = ""
                    tvDesc.text = ""
                }
            }

            "2" -> {
                with(binding) {
                    ivTipstricks.setImageResource(R.drawable.ic_large_cycle)
                    tvTipstricks.text = ""
                    tvDesc.text = ""
                }
            }

            "3" -> {
                with(binding) {
                    ivTipstricks.setImageResource(R.drawable.ic_large_bottle)
                    tvTipstricks.text = ""
                    tvDesc.text = ""
                }
            }

            "4" -> {
                with(binding) {
                    ivTipstricks.setImageResource(R.drawable.ic_large_plastic_bag)
                    tvTipstricks.text = ""
                    tvDesc.text = ""
                }
            }
        }
    }
}