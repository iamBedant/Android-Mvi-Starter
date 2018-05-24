package com.iambedant.mvistarter.di.module

import android.app.Application
import android.content.Context
import com.iambedant.mvistarter.util.schedulers.BaseSchedulerProvider
import com.iambedant.mvistarter.util.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by @iamBedant on 16/05/18.
 */
@Module
class AppModule(val application: Application) {
    @Provides
    @Singleton
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideSchedular(): BaseSchedulerProvider = SchedulerProvider()
}