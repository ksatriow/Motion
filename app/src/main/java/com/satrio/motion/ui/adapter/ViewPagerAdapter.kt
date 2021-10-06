package com.satrio.motion.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.satrio.motion.data.entity.Movie
import com.satrio.motion.ui.activity.main.viewpager.HomeViewPagerFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ViewPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    val movies: List<Movie> = ArrayList()
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = movies.size

    override fun createFragment(position: Int): Fragment {
        return HomeViewPagerFragment(movies[position])
    }

}