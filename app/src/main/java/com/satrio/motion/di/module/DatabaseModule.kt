package com.satrio.motion.di.module

import android.app.Application
import com.satrio.motion.data.local.BookmarkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application) = BookmarkDatabase.getInstance(application)

    @Provides
    @Singleton
    fun provideBookmarkDao(database: BookmarkDatabase) = database.getBookmarkDao()

}