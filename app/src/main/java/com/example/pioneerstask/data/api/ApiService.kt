package com.example.pioneerstask.data.api

import com.example.pioneerstask.data.model.GitModel
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("search/repositories")
    fun getData(
        @Query("q") created:String,
        @Query("sort") sort:String,
        @Query("order") order:String,
        @Query("per_page") per_page:String,
        @Query("q") lang:String,
    ): Observable<GitModel>

}