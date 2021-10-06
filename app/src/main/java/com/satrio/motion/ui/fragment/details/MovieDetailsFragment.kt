package com.satrio.motion.ui.fragment.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import coil.load
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.satrio.motion.R
import com.satrio.motion.data.entity.Cast
import com.satrio.motion.data.entity.Movie
import com.satrio.motion.data.entity.Status
import com.satrio.motion.databinding.FragmentMovieDetailsBinding
import com.satrio.motion.ui.adapter.CastAdapter
import com.satrio.motion.ui.adapter.SimilarMoviesAdapter
import com.satrio.motion.ui.videoplayer.VideoPlayerDisplay
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import com.satrio.motion.util.AppConstant
import com.satrio.motion.util.showToast
import com.satrio.motion.util.toHours

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var movie: Movie

    private val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var binding: FragmentMovieDetailsBinding

    private var castList: ArrayList<Cast> = ArrayList()
    private var similarList: ArrayList<Movie> = ArrayList()

    private lateinit var castAdapter: CastAdapter
    private lateinit var similarAdapter: SimilarMoviesAdapter

    private lateinit var castSkeleton: Skeleton
    private lateinit var similarMovieSkeleton: Skeleton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)
        binding = FragmentMovieDetailsBinding.bind(view)
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movie = requireArguments().get(AppConstant.movie) as Movie

        viewModel.movieName.value = movie.title
        viewModel.movie.value = movie
        viewModel.getVideos(movie.id)

        binding.buttonBack.setOnClickListener(this)
        binding.fabPlayButton.setOnClickListener(this)
        binding.buttonBookmark.setOnClickListener(this)

        initAdapters()

        loadData()

        loadCast()

        loadSimilar()

        checkBookmark()

        viewModel.getMovieDetails(movie.id)

    }

    private fun initAdapters() {
        similarAdapter = SimilarMoviesAdapter(requireContext(), similarList)
        castAdapter = CastAdapter(requireContext(), castList)

        binding.recyclerViewCast.adapter = castAdapter
        binding.recyclerViewRelated.adapter = similarAdapter

        castSkeleton = binding.recyclerViewCast.applySkeleton(R.layout.item_cast, 10)
        similarMovieSkeleton =
            binding.recyclerViewRelated.applySkeleton(R.layout.item_similar_movie, 10)
    }

    @SuppressLint("SetTextI18n")
    private fun loadData() {

        viewModel.movie.observe(requireActivity(), Observer {

            var genre: String = ""
            if (!it.genres.isNullOrEmpty())
                for (i in 0..it.genres.size - 1) {
                    genre += AppConstant.getGenreMap()[it.genres[i].id].toString()
                    if (i != it.genres.size - 1) {
                        genre += "• "
                    }
                }

            binding.apply {
                textMovieName.text = it!!.title
                textRating.text = "${it.vote_average}/10"
                textReleaseDate.text = it.release_date
                textDescription.text = it.overview
                if (it.runtime != null)
                    textLength.text = toHours(it.runtime!!)
                textGenres.text = genre

                detailsBannerImage.load(AppConstant.IMGBASEURL + it.backdrop_path) {
                    placeholder(AppConstant.viewPagerPlaceHolder.random())
                    error(AppConstant.viewPagerPlaceHolder.random())
                }

                imagePoster.load(AppConstant.IMGBASEURL + it.poster_path) {
                    placeholder(AppConstant.moviePlaceHolder.random())
                    error(AppConstant.moviePlaceHolder.random())
                }
            }

        })

    }

    private fun loadSimilar() {
        viewModel.loadSimilar(movie.id).observe(requireActivity(), Observer {
            when (it.status) {
                Status.LOADING -> {
                    if (similarList.isNotEmpty())
                        similarMovieSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    similarList.clear()
                    similarList.addAll(it.data!!.results)
                    similarAdapter.notifyDataSetChanged()
                    similarMovieSkeleton.showOriginal()

                    if (similarList.isNullOrEmpty()) {
                        binding.headingRelated.visibility = View.GONE
                    } else {
                        binding.headingRelated.visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }
        })
    }

    private fun loadCast() {
        viewModel.loadCast(movie.id).observe(requireActivity(), Observer {
            when (it.status) {
                Status.LOADING -> {
                    if (castList.isNullOrEmpty())
                        castSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    castSkeleton.showOriginal()
                    castList.clear()
                    castList.addAll(it.data!!.cast)
                    castAdapter.notifyDataSetChanged()

                    if (castList.isNullOrEmpty()) {
                        binding.headingCast.visibility = View.GONE
                    } else {
                        binding.headingCast.visibility = View.VISIBLE
                    }

                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }
        })
    }

    fun checkBookmark() {

        viewModel.bookmark.observe(viewLifecycleOwner, Observer {
            binding.apply {
                if (it) {
                    buttonBookmark.setImageResource(R.drawable.ic_bookmark_done)
                } else {
                    buttonBookmark.setImageResource(R.drawable.ic_bookmark)
                }
            }
        })

        viewModel.checkBookmarkExist()

    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.fabPlayButton -> {
                if (viewModel.videos.value != null && viewModel.videos.value!!.results.size != 0) {
                    val videoDialog = VideoPlayerDisplay(viewModel.videos.value!!.results[0].key)
                    videoDialog.show(childFragmentManager, "Video Dialog")
                } else {
                    showToast("Sorry video not found!")
                }
            }
            R.id.button_back -> {
                binding.root.findNavController().navigateUp()
            }
            R.id.button_bookmark -> {
                viewModel.bookmarkMovie()
                viewModel.checkBookmarkExist()
            }
        }

    }

}