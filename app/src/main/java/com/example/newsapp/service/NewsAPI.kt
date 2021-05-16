package com.example.newsapp.service

import com.example.newsapp.model.News
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): retrofit2.Call<News>

    @GET("everything")
    fun getSearchResults(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String
    ): retrofit2.Call<News>
}