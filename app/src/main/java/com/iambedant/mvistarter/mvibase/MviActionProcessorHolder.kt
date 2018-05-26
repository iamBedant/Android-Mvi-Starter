package com.iambedant.mvistarter.mvibase

import io.reactivex.ObservableTransformer

/**
 * Created by @iamBedant on 26/05/18.
 */
interface MviActionProcessorHolder<I: MviAction, R: MviResult>{
    fun transformFromAction(): ObservableTransformer<I, R>
}