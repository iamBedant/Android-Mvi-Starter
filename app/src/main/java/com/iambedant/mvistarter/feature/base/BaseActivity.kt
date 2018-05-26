package com.iambedant.mvistarter.feature.base

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.LayoutRes
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by @iamBedant on 26/05/18.
 */
abstract class BaseActivity: DaggerAppCompatActivity(), HasActivityInjector {
    @Inject
    lateinit var injector: DispatchingAndroidInjector<Activity>

    val disposable = CompositeDisposable()

    override fun activityInjector(): AndroidInjector<Activity> {
        return injector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        bind()
    }

    @LayoutRes
    abstract fun layoutId(): Int

    abstract fun bind()

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}