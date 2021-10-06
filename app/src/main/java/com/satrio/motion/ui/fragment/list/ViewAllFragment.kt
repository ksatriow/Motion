package com.satrio.motion.ui.fragment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.satrio.motion.R
import com.satrio.motion.data.entity.Movie
import com.satrio.motion.databinding.FragmentViewAllBinding
import com.satrio.motion.ui.adapter.BookmarkAdapter
import com.satrio.motion.ui.adapter.ViewAllAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.satrio.motion.util.AppConstant

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ViewAllFragment : Fragment() {

    private lateinit var binding: FragmentViewAllBinding
    private val viewModel: ViewAllViewModel by viewModels()

    private lateinit var movieAllAdapter: ViewAllAdapter
    private lateinit var movieSkeleton: Skeleton

    private var moviesList: ArrayList<Movie> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_all, container, false)
        binding = FragmentViewAllBinding.bind(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movieAllAdapter = ViewAllAdapter()
        binding.movieRecyclerView.adapter = movieAllAdapter

        movieSkeleton = binding.movieRecyclerView.applySkeleton(R.layout.item_search, 15)

        val pageType = requireArguments().get(AppConstant.viewAll)
        binding.pageTitle.text = pageType.toString()
        when (pageType) {
            AppConstant.Upcoming -> fetchUpcoming()
            AppConstant.TopRated -> fetchTopRated()
            AppConstant.Popular -> fetchPopular()
            AppConstant.Bookmarks -> fetchBookmarks()
        }

        binding.buttonBack.setOnClickListener {
            binding.root.findNavController().navigateUp()
        }

    }

    fun fetchBookmarks() {

        viewModel.bookmarks.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.movieRecyclerView.adapter = BookmarkAdapter(it)
            }
        }

        viewModel.fetchBookmarks()

    }

    fun fetchPopular() {
        viewModel.fetchPopular().observe(viewLifecycleOwner) {

            movieAllAdapter.submitData(viewLifecycleOwner.lifecycle, it)

        }
    }

    fun fetchTopRated() {

        viewModel.fetchTopRated().observe(viewLifecycleOwner) {

            movieAllAdapter.submitData(viewLifecycleOwner.lifecycle, it)

        }

    }

    fun fetchUpcoming() {

        viewModel.fetchUpcoming().observe(viewLifecycleOwner) {

            movieAllAdapter.submitData(viewLifecycleOwner.lifecycle, it)

        }

    }

}