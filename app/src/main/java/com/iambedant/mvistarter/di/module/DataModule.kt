package com.iambedant.mvistarter.di.module

import com.iambedant.mvistarter.data.Repository
import com.iambedant.mvistarter.data.RepositoryImpl
import com.iambedant.mvistarter.data.local.Local
import com.iambedant.mvistarter.data.remote.Network
import com.iambedant.mvistarter.util.schedulers.BaseSchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * Created by @iamBedant on 17/05/18.
 */

@Module
class DataModule {
    @Provides
    fun provideRepository(service: Network, local: Local, schedulerProvider: BaseSchedulerProvider): Repository {
        return RepositoryImpl(service,local,schedulerProvider)
    }
}