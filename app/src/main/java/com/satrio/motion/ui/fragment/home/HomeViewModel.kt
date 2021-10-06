package com.satrio.motion.ui.fragment.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.satrio.motion.data.entity.Resource
import com.satrio.motion.data.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import java.net.SocketTimeoutException
import javax.inject.Inject

@ExperimentalCoroutinesApi
class HomeViewModel @ViewModelInject constructor(
    private val repository : NetworkRepository
) : ViewModel() {

    fun loadNowPlaying() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val apiResponse = repository.getNowPlayingMovie()
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun loadUpcoming() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val apiResponse = repository.getUpcomingMovie()
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun loadPopular() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val apiResponse = repository.getPopularMovie()
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun loadTopRated() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val apiResponse = repository.getTopRatedMovie()
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }


}