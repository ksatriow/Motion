package com.satrio.motion.ui.fragment.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.satrio.motion.data.entity.Resource
import com.satrio.motion.data.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import java.net.SocketTimeoutException

class SearchViewModel @ViewModelInject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {

    fun searchMovie(query: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val apiResponse =
                networkRepository.getSearchResult(query).cachedIn(viewModelScope)
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun getSearchMovie(query: String) =
        networkRepository.getSearchResult(query).cachedIn(viewModelScope)


}