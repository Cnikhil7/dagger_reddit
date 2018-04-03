package org.loop.example

import io.reactivex.Single
import org.loop.example.models.pojo.RedditResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditApiInterface {


    @GET("{listing}.json")
    fun getListFromListing(@Path("listing") listing: String
                           , @Query("limit") limit: Int = 12): Single<RedditResponse>


    @GET("{listing}.json")
    fun getListFromListingAfter(@Path("listing") listing: String
                                , @Query("after") nextKey: String
                                , @Query("limit") limit: Int = 12): Single<RedditResponse>

}