package com.iambedant.mvistarter.di.module

import com.iambedant.mvistarter.data.Repository
import com.iambedant.mvistarter.data.RepositoryImpl
import com.iambedant.mvistarter.data.remote.NetworkService
import dagger.Module
import dagger.Provides

/**
 * Created by @iamBedant on 17/05/18.
 */

@Module
class DataModule {
    @Provides
    fun provideRepository(service: NetworkService): Repository {
        return RepositoryImpl(service)
    }
}