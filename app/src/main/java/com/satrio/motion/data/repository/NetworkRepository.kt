package com.satrio.motion.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.satrio.motion.data.network.ApiRequest
import com.satrio.motion.data.network.NetworkService
import com.satrio.motion.paging.PopularPagingSource
import com.satrio.motion.paging.SearchPagingSource
import com.satrio.motion.paging.TopRatedPagingSource
import com.satrio.motion.paging.UpcomingPagingSource
import com.satrio.motion.util.AppConstant
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val networkApi: NetworkService
) : ApiRequest() {

    suspend fun getPopularMovie() = apiRequest {
        networkApi.getPopularMovies(AppConstant.APIKEY)
    }

    suspend fun getTopRatedMovie() = apiRequest {
        networkApi.getTopRatedMovies(AppConstant.APIKEY)
    }

    suspend fun getUpcomingMovie() = apiRequest {
        networkApi.getUpcomingMovies(AppConstant.APIKEY)
    }

    suspend fun getNowPlayingMovie() = apiRequest {
        networkApi.getNowPlayingMovies(AppConstant.APIKEY)
    }

    suspend fun getMovieDetails(movie_id: Int) = apiRequest {
        networkApi.getMovieById(movie_id, AppConstant.APIKEY)
    }

    suspend fun getVideos(movie_id: Int) = apiRequest {
        networkApi.getVideos(movie_id, AppConstant.APIKEY)
    }

    suspend fun getMovieCredits(movie_id: Int) = apiRequest {
        networkApi.getMovieCredits(movie_id, AppConstant.APIKEY)
    }

    suspend fun getSimilarMovies(movie_id: Int) = apiRequest {
        networkApi.getSimilarMovies(movie_id, AppConstant.APIKEY)
    }

    suspend fun getPerson(personId: Int) = apiRequest {
        networkApi.getPerson(personId, AppConstant.APIKEY)
    }

    suspend fun getPersonMovieCredits(personId: Int) = apiRequest {
        networkApi.getPersonMovieCredits(personId, AppConstant.APIKEY)
    }

    suspend fun searchMovie(query: String, page: Int) = apiRequest {
        networkApi.searchMovie(query, page, AppConstant.APIKEY)
    }

    fun getSearchResult(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 500
            ),
            pagingSourceFactory = {
                SearchPagingSource(networkApi, query)
            }
        ).liveData

    fun getPopularMovieResult() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 500
            ),
            pagingSourceFactory = {
                PopularPagingSource(networkApi)
            }
        ).liveData

    fun getUpcomingMovieResult() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 500
            ),
            pagingSourceFactory = {
                UpcomingPagingSource(networkApi)
            }
        ).liveData

    fun getTopRatedMovieResult() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 500
            ),
            pagingSourceFactory = {
                TopRatedPagingSource(networkApi)
            }
        ).liveData

}