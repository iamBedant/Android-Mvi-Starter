package com.iambedant.mvistarter.di.module

import com.iambedant.mvistarter.feature.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by @iamBedant on 16/05/18.
 */
@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [(MviModule::class)])
    abstract fun loginActivity(): LoginActivity
}