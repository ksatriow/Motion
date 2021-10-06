package com.satrio.motion.di.module

import com.satrio.motion.data.network.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import com.satrio.motion.util.AppConstant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApiModule {

    @Provides
    @Singleton
    operator fun invoke() : NetworkService {
        return Retrofit.Builder()
                .baseUrl(AppConstant.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkService::class.java)
    }


}