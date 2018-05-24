package com.iambedant.mvistarter.di.component

import com.iambedant.mvistarter.MviApp
import com.iambedant.mvistarter.di.module.ActivityBindingModule
import com.iambedant.mvistarter.di.module.AppModule
import com.iambedant.mvistarter.di.module.NetworkModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by @iamBedant on 23/05/18.
 */

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    NetworkModule::class,
    ActivityBindingModule::class
])

interface AppComponent : AndroidInjector<MviApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MviApp>() {

        abstract fun appModule(appModule: AppModule): Builder

        override fun seedInstance(instance: MviApp?) {
            appModule(AppModule(instance!!))
        }
    }
}