package com.example.newsapp.api

import com.example.newsapp.model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiInterface {
    @GET("top-headlines")
    fun getTopHeadlines(@Query("country") country: String, @Query("apiKey") apiKey: String) : Call<News>
}