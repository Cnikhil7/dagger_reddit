package org.loop.example

import org.loop.example.models.pojo.RedditResponse

/**
 * wrapper interface for dataloader class
 */

interface DataLoad {

    interface Callback {
        fun onSuccess(response: RedditResponse)

        fun onFailure()
    }

    fun getResults(callback: Callback)
    fun getNextResultsWithKey(key : String , callback: Callback)

    fun onDestroy()
}
