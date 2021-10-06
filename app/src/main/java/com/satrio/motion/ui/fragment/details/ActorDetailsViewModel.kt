package com.satrio.motion.ui.fragment.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.satrio.motion.data.entity.Resource
import com.satrio.motion.data.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import java.net.SocketTimeoutException

class ActorDetailsViewModel @ViewModelInject constructor(
    private val repository: NetworkRepository
) : ViewModel() {

    fun getPerson(person_id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val apiResponse = repository.getPerson(person_id)
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun getPersonMovieCredits(person_id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val apiResponse = repository.getPersonMovieCredits(person_id)
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException) {
                emit(Resource.error("Something went wrong!"))
            }
        }

    }


}