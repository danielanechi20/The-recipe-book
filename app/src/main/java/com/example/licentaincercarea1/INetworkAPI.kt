package com.example.licentaincercarea1

import com.example.licentaincercarea1.data.category
import io.reactivex.Observable
import retrofit2.http.GET

interface INetworkAPI {

    @GET("posts/")
    fun getAllPosts(): Observable<List<category>>
}