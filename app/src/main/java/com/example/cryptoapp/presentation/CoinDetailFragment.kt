package com.example.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityCoinDetailBinding
import com.example.cryptoapp.databinding.FragmentCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailFragment : Fragment() {

    private lateinit var viewModel: CoinViewModel

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding: FragmentCoinDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fsym = getSymbol()
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        fsym.let {
            viewModel.getDetailInfo(fsym).observe(viewLifecycleOwner, Observer {
                with(binding)
                {
                    tvPrice.text = it.price.toString()
                    tvMinPrice.text = it.lowDay.toString()
                    tvMaxPrice.text = it.highDay.toString()
                    tvLastMarket.text = it.lastMarket
                    tvLastUpdate.text = it.lastUpdate
                    tvFromSymbol.text = it.fromSymbol
                    tvToSymbol.text = it.toSymbol
                    Picasso.get().load(it.imageUrl).into(ivLogoCoin)
                }

            })
        }
    }

    private fun getSymbol(): String {
        return requireArguments().getString(EXTRA_FROM_FSYM) ?: ""
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        private const val EXTRA_FROM_FSYM = "fsym"

        fun newInstance(fromSymbol: String): Fragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_FROM_FSYM, fromSymbol)
                }
            }
        }
    }
}