package com.satrio.motion.ui.activity.main.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import coil.load
import com.satrio.motion.R
import com.satrio.motion.data.entity.Movie
import com.satrio.motion.databinding.FragmentViewPagerBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.satrio.motion.util.AppConstant

@ExperimentalCoroutinesApi
class HomeViewPagerFragment(
    val movie: Movie
) : Fragment() {

    private lateinit var binding : FragmentViewPagerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)
        binding = FragmentViewPagerBinding.bind(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewPagerImage.load(AppConstant.IMGBASEURL + movie.backdrop_path) {
            placeholder(AppConstant.viewPagerPlaceHolder.last())
            error(AppConstant.viewPagerPlaceHolder.last())
        }
        binding.viewPagerText.text = movie.title

        binding.root.setOnClickListener {
            val bundle = bundleOf(AppConstant.movie to movie)
            it.findNavController().navigate(R.id.action_homeFragment_to_movieDetailsFragment, bundle)
        }

    }
}