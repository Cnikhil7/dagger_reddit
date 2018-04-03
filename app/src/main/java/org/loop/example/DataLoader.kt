package org.loop.example

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.loop.example.di.MyApplication
import org.loop.example.models.pojo.RedditResponse
import javax.inject.Inject

class DataLoader : DataLoad {


    companion object {
        var isFetching: Boolean = false
        var disposable: CompositeDisposable? = null
    }

    init {
        MyApplication.component.inject(this)
    }

    @Inject
    lateinit var redditService: RedditApiInterface

    override fun getResults(callback: DataLoad.Callback) {
        if (disposable == null) disposable = CompositeDisposable()

        val endPointResponse: Single<RedditResponse> =
                redditService.getListFromListing(listing = "best")

        if (!isFetching) {
            isFetching = true
            callApi(callback, endPointResponse)
        } else {
            //ignore or identify and queue
        }

    }

    private fun callApi(callback: DataLoad.Callback, endPointResponse: Single<RedditResponse>) {
        disposable?.add(endPointResponse.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RedditResponse>() {
                    override fun onError(e: Throwable) {
                        callback.onFailure()
                        isFetching = false
                    }

                    override fun onSuccess(t: RedditResponse) {
                        callback.onSuccess(t)
                        isFetching = false
                    }
                }))
    }

    override fun getNextResultsWithKey(key: String, callback: DataLoad.Callback) {
        if (disposable == null) disposable = CompositeDisposable()

        val endPointResponse: Single<RedditResponse> = redditService
                .getListFromListingAfter(listing = "best", nextKey = key)

        if (!isFetching) {
            isFetching = true
            callApi(callback, endPointResponse)
        }
    }


    override fun onDestroy() {
        disposable?.clear()
    }githu


}