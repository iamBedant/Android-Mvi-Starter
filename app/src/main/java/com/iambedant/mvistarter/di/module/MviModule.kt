package com.iambedant.mvistarter.di.module

import com.iambedant.mvistarter.di.ActivityScope
import com.iambedant.mvistarter.feature.home.HomeActionProcessorHolder
import com.iambedant.mvistarter.feature.home.HomeViewmodelFactory
import com.iambedant.mvistarter.feature.login.LoginActionProcessorHolder
import com.iambedant.mvistarter.feature.login.LoginViewmodelFactory
import dagger.Module
import dagger.Provides

/**
 * Created by @iamBedant on 16/05/18.
 */
@Module(includes = [DataModule::class])
class MviModule {

    @Provides
    @ActivityScope
    fun provideloginViewmodelFactory(actionProcessorHolder: LoginActionProcessorHolder): LoginViewmodelFactory {
        return LoginViewmodelFactory(actionProcessorHolder)
    }

    @Provides
    @ActivityScope
    fun provideHomeViewmodelFactory(actionProcessorHolder: HomeActionProcessorHolder): HomeViewmodelFactory {
        return HomeViewmodelFactory(actionProcessorHolder)
    }


}