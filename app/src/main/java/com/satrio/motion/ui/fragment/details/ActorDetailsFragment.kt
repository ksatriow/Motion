package com.satrio.motion.ui.fragment.details

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
import com.satrio.motion.databinding.FragmentActorDetailsBinding
import com.satrio.motion.ui.adapter.BestMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.satrio.motion.util.AppConstant
import com.satrio.motion.util.showToast

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ActorDetailsFragment : Fragment() {

    private val viewModel: ActorDetailsViewModel by viewModels()

    private lateinit var binding: FragmentActorDetailsBinding
    private lateinit var cast: Cast



    private var movieCredits: ArrayList<Movie> = ArrayList()
    private lateinit var homeAdapter: BestMoviesAdapter

    private lateinit var skeletonBestMovies: Skeleton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_actor_details, container, false)
        binding = FragmentActorDetailsBinding.bind(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        cast = requireArguments().get(AppConstant.cast) as Cast

        binding.buttonBack.setOnClickListener {
            it.findNavController().navigateUp()
        }

        initAdapters()

        fetchDataActor()

    }

    private fun fetchDataActor() {
        viewModel.getPerson(cast.id).observe(requireActivity(), Observer {
            val actor = it.data
            if (actor != null) {
                binding.textActorName.text = actor.name
                binding.actorImage.load(AppConstant.IMGBASEURL + actor.profile_path)

                var knownAs = ""
                for (i in 0..Math.min(4, actor.also_known_as.size) - 1) {
                    knownAs += actor.also_known_as[i]
                    if (i != actor.also_known_as.size - 1) {
                        knownAs += ", "
                    }
                }
                binding.textAlsoKnownAs.text = knownAs

                binding.textBio.text = actor.biography

                binding.textPopularity.text = actor.popularity.toString()
                binding.textCharacter.text = actor.known_for_department
                binding.textBirthday.text = actor.birthday
            }
        })

        viewModel.getPersonMovieCredits(cast.id).observe(requireActivity(), Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    skeletonBestMovies.showSkeleton()
                }
                Status.SUCCESS -> {
                    skeletonBestMovies.showOriginal()
                    movieCredits.clear()
                    movieCredits.addAll(res.data!!.cast)
                    homeAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }
        })
    }

    private fun initAdapters() {
        homeAdapter = BestMoviesAdapter(requireContext(), movieCredits)
        binding.recyclerViewBestMovies.adapter = homeAdapter
        skeletonBestMovies =
            binding.recyclerViewBestMovies.applySkeleton(R.layout.item_movie_home, 10)
    }

}