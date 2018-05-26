package com.iambedant.mvistarter.feature.home

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager

import com.iambedant.mvistarter.R
import com.iambedant.mvistarter.data.remote.model.ArticlesItem
import com.iambedant.mvistarter.mvibase.MviView
import com.iambedant.mvistarter.util.gone
import com.iambedant.mvistarter.util.visible
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject
import android.content.Intent
import com.iambedant.mvistarter.feature.base.BaseActivity


class HomeActivity : BaseActivity(), MviView<HomeIntent, HomeViewState>{

    override fun bind() {
        newsRv.layoutManager = GridLayoutManager(this, 1)
        viewModel.processIntents(intents())
        viewModel.states().observe(this, Observer { if (it != null) render(it) })
    }

    override fun layoutId(): Int = R.layout.activity_home


    @Inject
    lateinit var factory: HomeViewmodelFactory

    private val clickIntent = PublishSubject.create<HomeIntent.ClickIntent>()



    private val viewModel: HomeViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
    }

    private fun initialIntent(): Observable<HomeIntent.InitialIntent> {
        return Observable.just(HomeIntent.InitialIntent)
    }

    override fun intents(): Observable<HomeIntent> {
        return Observable.merge(initialIntent(), clickIntent)
    }

    override fun render(state: HomeViewState) {
        with(state) {
            if (isLoading) {
                progressBar.visible()
            } else {
                progressBar.gone()
            }
            if (!articles.isEmpty()) {
                newsRv.adapter = NewsAdapter(articles, { clickItem -> clickIntent.onNext(HomeIntent.ClickIntent(clickItem)) })
            }
            if(showShareOption){
                showShareIntent(shareArticle)
            }

        }
    }

    private fun showShareIntent(shareArticle: ArticlesItem?) {
        shareArticle?.let {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    shareArticle.title)
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
    }


    override fun activityInjector(): AndroidInjector<Activity> {
        return injector
    }
}
