package com.iambedant.mvistarter.feature.login

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.Toast
import com.iambedant.mvistarter.R
import com.iambedant.mvistarter.util.gone
import com.iambedant.mvistarter.util.shortToast
import com.iambedant.mvistarter.util.visible
import com.iambedant.pizzaapp.mvibase.MviView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity(), MviView<LoginIntent, LoginViewState>, HasActivityInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var factory: LoginViewmodelFactory

    val disposable = CompositeDisposable()

    private val viewModel: LoginViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
    }

    private fun initialIntent(): Observable<LoginIntent.InitialIntent> {
        return Observable.just(LoginIntent.InitialIntent)
    }

    private val doLoginIntent = PublishSubject.create<LoginIntent.DoLoginIntent>()
    private val typeUserIdIntent = PublishSubject.create<LoginIntent.typeUserIdIntent>()
    private val typePasswordIntent = PublishSubject.create<LoginIntent.typePasswordIntent>()


    override fun activityInjector(): AndroidInjector<Activity> {
        return injector
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bind()
    }

    private fun bind() {
        viewModel.processIntents(intents())
        viewModel.states().observe(this, Observer { if (it != null) render(it) })


        login.setOnClickListener { doLoginIntent.onNext(LoginIntent.DoLoginIntent(
                userId = userIdEt.text.toString(),
                password = passwordEt.text.toString())) }

        disposable.add(RxTextView.textChanges(userIdEt).debounce(300, TimeUnit.MILLISECONDS).subscribe { text -> typeUserIdIntent.onNext(LoginIntent.typeUserIdIntent(text.toString())) })
        disposable.add(RxTextView.textChanges(passwordEt).debounce(300, TimeUnit.MILLISECONDS).subscribe { text -> typePasswordIntent.onNext(LoginIntent.typePasswordIntent(text.toString())) })
    }

    override fun intents(): Observable<LoginIntent> {
        return Observable.merge(initialIntent(), doLoginIntent, typeUserIdIntent, typePasswordIntent)
    }

    override fun render(state: LoginViewState) {
        with(state) {
            when (isLoading) {
                true -> {
                    progressBar.visible()
                    login.isEnabled = false
                }
                else -> {
                    progressBar.gone()
                    login.isEnabled = true
                }
            }
            if (userIdEt.text.toString().isEmpty()) {
                with(userIdEt) {
                    setText(userId)
                    setSelection(userId.length)
                }

            }
            if (userIdEt.text.toString().isEmpty()) {
                with(passwordEt) {
                    setText(password)
                    setSelection(password.length)
                }

            }
            if(isError){
                errorMessage.shortToast(this@LoginActivity)
                login.isEnabled = true
                progressBar.gone()
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
