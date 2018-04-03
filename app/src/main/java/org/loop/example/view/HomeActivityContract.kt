package org.loop.example.view

import org.loop.example.BasePresenter
import org.loop.example.BaseView
import org.loop.example.models.pojo.RedditResponse

class HomeActivityContract {

    interface View : BaseView<Presenter> {
        override fun setPresenter(presenter: Presenter)
        fun onDataLoadError()
        fun onDataLoadSuccess(response : RedditResponse)
        fun showProgress(visibility : Boolean)
    }

    interface Presenter : BasePresenter {
        override fun onCreate()

        override fun onStop()

        fun loadInitialResults(forceRefresh : Boolean = false)
        fun loadNextResults(nextKey: String)
    }

}
