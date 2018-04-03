package org.loop.example.view

import org.loop.example.DataLoad
import org.loop.example.DataLoader
import org.loop.example.models.pojo.RedditResponse

class HomePresenter(var view: HomeActivityContract.View, var dataLoader: DataLoader)
    : HomeActivityContract.Presenter {


    companion object {
        var firstInit = true
    }

    override fun onCreate() {
        loadInitialResults(true)
    }

    override fun loadInitialResults(forceRefresh: Boolean) {

        if (forceRefresh || firstInit) {

            view.showProgress(true)

            dataLoader.getResults(object : DataLoad.Callback {
                override fun onFailure() {
                    view.onDataLoadError()
                    view.showProgress(false)
                }

                override fun onSuccess(response: RedditResponse) {
                    view.onDataLoadSuccess(response)
                    view.showProgress(false)
                }
            })
        }

        firstInit = false
    }

    override fun loadNextResults(nextKey: String) {
        view.showProgress(true)

        dataLoader.getNextResultsWithKey(nextKey, object : DataLoad.Callback{
            override fun onSuccess(response: RedditResponse) {
                view.onDataLoadSuccess(response)
                view.showProgress(false)
            }

            override fun onFailure() {
                view.onDataLoadError()
                view.showProgress(false)
            }
        })
    }


    override fun onStop() {
        dataLoader.onDestroy()//clear disposable
    }
}